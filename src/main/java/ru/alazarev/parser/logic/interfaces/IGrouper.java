package ru.alazarev.parser.logic.interfaces;

import ru.alazarev.parser.entities.interfaces.IGroup;

import java.util.Set;

/**
 * Class IGrouper
 * <p>
 * Date: 13.03.2020
 *
 * @author a.lazarev
 */
public interface IGrouper {
    /**
     * Method groups strings.
     *
     * @return Map, where Column is key and Group is value.
     */
    Set<IGroup> group();
}
