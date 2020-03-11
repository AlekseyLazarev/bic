package ru.alazarev.parser;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class Parser
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class);
    private String regex = "^\"\\d*\";\"\\d*\";\"\\d*\"$";
    private String filePath = ClassLoader.getSystemResource("lng.csv").getFile();
    private String delimiter = ";";
    private Set<Entity> entities = new HashSet<>();
    private Set<Group> uniqGroups;

    /**
     * Method sets regular expression for parser.
     *
     * @param regex Regular expression.
     */
    public void setRegex(String regex) {
        this.regex = regex;
    }

    /**
     * Method sets path to file for parsing.
     *
     * @param filePath File path string.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method sets delimiter between columns.
     *
     * @param delimiter String delimiter.
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Method return unique entities.
     *
     * @return Set of entities.
     */
    public Set<Entity> getEntities() {
        return entities;
    }

    /**
     * Method parse file and records uniq groups.
     */
    public void parse() {
        logger.info("Start parsing file " + this.filePath);
        Pattern pattern = Pattern.compile(this.regex);
        Map<Column, Group> groupMap = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (pattern.matcher(line).find()) {
                    String[] current = line.split(this.delimiter);
                    Entity entity = new Entity(current);
                    if (this.entities.add(entity)) {
                        List<Column> noEmptyColumns = entity.getNotEmptyColumns();
                        Group currentGroup = null;
                        for (Column cv : noEmptyColumns) {
                            currentGroup = groupMap.get(cv);
                            if (currentGroup != null)
                                break;
                        }
                        if (currentGroup == null)
                            currentGroup = new Group();
                        currentGroup.add(entity);
                        for (Column cv : noEmptyColumns) {
                            groupMap.put(cv, currentGroup);
                        }
                    }
                }
            }
            this.uniqGroups = new HashSet<>(groupMap.values());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Method sort groups by size and return sorted list of groups.
     *
     * @return Sorted List of Group.
     */
    public List<Group> sort() {
        logger.info("Sorting groups . . .");
        return this.uniqGroups.stream().sorted((o1, o2) -> Integer.compare(o2.getSize(), o1.getSize())).collect(Collectors.toList());
    }

    /**
     * Method returns the number of groups with size above border.
     *
     * @param border Lower bar of groups size.
     * @return number of groups.
     */
    public long groupCounter(int border) {
        logger.info("Counting the number of groups . . .");
        return this.uniqGroups.stream().filter(group -> group.getSize() > border).count();
    }

    /**
     * Method print groups in print stream.
     *
     * @param groups      Collection of group.
     * @param printStream Stream for print.
     */
    private void printGroups(Collection<Group> groups, PrintStream printStream) {
        logger.info("Printing groups . . .");
        AtomicInteger countGroup = new AtomicInteger();
        groups.forEach(group -> printStream.println("Группа " + countGroup.getAndIncrement() + System.lineSeparator() +
                group.toString()));
    }

    /**
     * Method sets the print format for the job.
     *
     * @param printStream Stream for print.
     * @param border      Lower bar of groups size.
     */
    public void formattedPrint(PrintStream printStream, int border) {
        logger.info("Final formatted print . . .");
        printStream.println("Групп с более чем " + border + " элементом = " + groupCounter(border));
        List<Group> sortedGroups = sort();
        printGroups(sortedGroups, printStream);
        printStream.println("Количество полученных групп = " + this.uniqGroups.size());
    }

}
