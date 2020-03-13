package ru.alazarev.parser.logic.implementations;

import ru.alazarev.parser.entities.interfaces.IGroup;
import ru.alazarev.parser.utils.ParserUtils;
import ru.alazarev.parser.logic.interfaces.IParserOut;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.alazarev.parser.Log.logger;

/**
 * Class ParserOutToFile
 * <p>
 * Date: 13.03.2020
 *
 * @author a.lazarev
 */
public class ParserOutToFile implements IParserOut {
    private List<IGroup> groups;
    private String filePath;
    private int border;

    /**
     * Constructor.
     *
     * @param groups   List of groups.
     * @param filePath Output print stream.
     * @param border   Lower bar of groups size.
     */
    public ParserOutToFile(List<IGroup> groups, String filePath, int border) {
        this.filePath = filePath;
        this.groups = groups;
        this.border = border;
    }

    @Override
    public void print() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath))) {
            bw.write("Групп с более чем " + this.border + " элементом = " + ParserUtils.groupCounter(this.groups, this.border));
            bw.newLine();
            AtomicInteger countGroup = new AtomicInteger();
            for (IGroup group : this.groups) {
                bw.write("Группа " + countGroup.getAndIncrement() + System.lineSeparator() + group.toString());
            }
            bw.write("Количество полученных групп = " + this.groups.size());
        } catch (IOException e) {
            logger.info(e);
        }
    }
}
