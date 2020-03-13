package ru.alazarev.parser;

import ru.alazarev.parser.entities.interfaces.IGroup;
import ru.alazarev.parser.logic.implementations.Grouper;
import ru.alazarev.parser.logic.implementations.ParserFromFile;
import ru.alazarev.parser.logic.implementations.ParserOutToFile;
import ru.alazarev.parser.utils.ParserUtils;
import ru.alazarev.parser.logic.interfaces.IGrouper;
import ru.alazarev.parser.logic.interfaces.IParser;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static ru.alazarev.parser.Log.logger;

/**
 * Class App
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class App {
    /**
     * Method return duration between two time.
     *
     * @param s Start time instant.
     * @param f Finish time instant.
     * @return duration between start time and finish time in seconds.
     */
    public static String timeView(Instant s, Instant f) {
        Duration d = Duration.between(s, f);
        return d.toString().substring(2);
    }

    /**
     * Method log in logger or console, depending on flag.
     *
     * @param logValue Text log.
     * @param flag     if true, log in logger, if false log in console
     */
    public static void log(String logValue, boolean flag) {
        if (flag) {
            logger.info(logValue);
        } else {
            System.out.println(logValue);
        }
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        String outputFilePath = "C://TMP//result.txt";
        int border = 1;
        Instant step = start;
//        String filePath = ClassLoader.getSystemResource("partLng.csv").getFile();
        String filePath = ClassLoader.getSystemResource("lng.csv").getFile();
        String regexp = "^\"\\d*\";\"\\d*\";\"\\d*\"$";
        boolean flag = false;
        log("Start parse from file " + filePath + " with regexp " + regexp + " . . .", flag);
        IParser parserFromFile = new ParserFromFile(regexp, filePath);
        log("Parse complete " + timeView(step, step = Instant.now()), flag);

        log("Start grouping . . .", flag);
        IGrouper grouper = new Grouper(parserFromFile.parse(), ";");
        log("Create groups complete " + timeView(step, step = Instant.now()), flag);

        log("Start sort . . .", flag);
        List<IGroup> sortedGroup = ParserUtils.sortBySize(grouper.group());
        log("Sort groups complete " + timeView(step, step = Instant.now()), flag);

        log("Start output in file " + outputFilePath + " with border = " + border + " . . .", flag);
        ParserOutToFile pp = new ParserOutToFile(sortedGroup, outputFilePath, border);
        pp.print();
        log("Output in file complete " + timeView(step, Instant.now()), flag);

        log("Full time = " + timeView(start, Instant.now()), flag);
    }
}
