package ru.alazarev.parser.entities.interfaces;

import java.util.List;

/**
 * Class IRecord
 * <p>
 * Date: 12.03.2020
 *
 * @author a.lazarev
 */
public interface IRecord {
    /**
     * Method returns list of not empty values.
     *
     * @return List of not empty values.
     */
    List<IValue> getNotEmptyValues();
    List<IValue> getColumns();
}
