package ru.alazarev.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class ParserTest
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParserTest {
    private Parser parser;

    private long getRandomLong(int length) {
        long bottom = 1;
        for (int i = 1; i < length; i++) {
            bottom *= 10;
        }
        return ThreadLocalRandom.current().nextLong(bottom, bottom * 10 - 1);
    }

    private String getNewLine(long f, long s, long t) {
        return String.format("\"%11d\";\"%15d\";\"%15d\"" + System.lineSeparator(), f, s, t);
    }

    private String generateIdentityRecords(String filePath, int numberOfRecords, int numberOfIdentical) {
        return generateCsv(filePath, numberOfRecords, numberOfIdentical, true, 0);
    }

    private String generateIdentityColumns(String filePath, int numberOfRecords, int numberOfIdentical, int identCount) {
        return generateCsv(filePath, numberOfRecords, numberOfIdentical, false, identCount);
    }

    private String generateCsv(String filePath, int numberOfRecords, int numberOfIdentical, boolean flag, int identCount) {
        File file = new File(filePath);
        int fl = 11;
        int stl = 15;
        try (PrintStream ps = new PrintStream(new FileOutputStream(file))) {
            for (int i = 0; i < numberOfRecords; i++) {
                long f = getRandomLong(fl);
                long s = getRandomLong(stl);
                long t = getRandomLong(stl);
                String current = getNewLine(f, s, t);
                ps.print(current);
                if (flag && numberOfIdentical > 0) {
                    ps.print(current);
                    i++;
                    numberOfIdentical--;
                } else {
                    for (int l = 1; numberOfIdentical-- > 0 && l < identCount; l++) {
                        ps.print(getNewLine(getRandomLong(fl), getRandomLong(stl), t));
                        i++;
                    }
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    @BeforeEach
    public void setUp() {
        this.parser = new Parser();
    }

    @Test
    public void whenGetUniqEntitiesThenSetContainOnlyUniq() {
        int records = 10;
        int ident = 2;
        String currentPath = generateIdentityRecords("1test.csv", records, ident);
        this.parser.setFilePath(currentPath);
        this.parser.parse();
        Set s = this.parser.getEntities();
        assertEquals(records - ident, s.size());
        new File(currentPath).delete();
    }

    @Test
    public void whenGetGroupCounterByBorderFourThenReturnFive() {
        int records = 100;
        int ident = 20;
        int identCount = 4;
        String currentPath = generateIdentityColumns("2test.csv", records, ident, identCount);
        this.parser.setFilePath(currentPath);
        this.parser.parse();
        assertEquals(ident / identCount, this.parser.groupCounter(identCount - 1));
        new File(currentPath).delete();
    }

    @Test
    public void whenSortedThenInFirstGroupTenInSecondOne() {
        int records = 100;
        int ident = 10;
        int identCount = ident;
        String currentPath = generateIdentityColumns("3test.csv", records, ident, identCount);
        this.parser.setFilePath(currentPath);
        this.parser.parse();
        List<Group> sortedGroups = this.parser.sort();
        assertEquals(identCount, sortedGroups.get(0).getSize());
        assertEquals(1, sortedGroups.get(1).getSize());
        new File(currentPath).delete();
    }
}