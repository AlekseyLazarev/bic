package ru.alazarev.parser.logic.interfaces;

import java.util.List;

/**
 * Class IParser
 * <p>
 * Date: 13.03.2020
 *
 * @author a.lazarev
 */
public interface IParser {
    /**
     * Method parsing to List of strings.
     *
     * @return List of strings.
     */
    List<String> parse();
}