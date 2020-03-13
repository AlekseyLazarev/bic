package ru.alazarev.parser.logic.implementations;

import ru.alazarev.parser.logic.interfaces.IParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static ru.alazarev.parser.Log.logger;

/**
 * Class ParserFromFile
 * <p>
 * Date: 13.03.2020
 *
 * @author a.lazarev
 */
public class ParserFromFile implements IParser {
    private final String regex;
    private final String filePath;

    /**
     * Constructor.
     *
     * @param regex    Regular expression to validate.
     * @param filePath File path.
     */
    public ParserFromFile(String regex, String filePath) {
        this.regex = regex;
        this.filePath = filePath;
    }

    /**
     * Parsing method from file to List of strings.
     *
     * @return List of strings.
     */
    @Override
    public List<String> parse() {
        List<String> strings = new LinkedList<>();
        Pattern pattern = Pattern.compile(this.regex);
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (pattern.matcher(line).find()) {
                    strings.add(line);
                }
            }
        } catch (IOException ex) {
            logger.info(ex);
        }
        return strings;
    }
}