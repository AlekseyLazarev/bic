package ru.alazarev.parser;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

/**
 * Class App
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class App {

    public static void main(String[] args) {
        Instant start = Instant.now();
        try (PrintStream ps = new PrintStream(new FileOutputStream("C://TMP//result.txt"))) {
            Parser parser = new Parser();
            parser.parse();
            parser.formattedPrint(ps, 1);
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        System.out.println("Completed in " + Duration.between(start, Instant.now()).toSeconds() + "s");
    }
}
