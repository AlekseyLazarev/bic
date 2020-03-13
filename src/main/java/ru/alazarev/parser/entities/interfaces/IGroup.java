package ru.alazarev.parser.entities.interfaces;

import java.util.List;
import java.util.Set;

/**
 * Class IGroup
 * <p>
 * Date: 12.03.2020
 *
 * @author a.lazarev
 */
public interface IGroup {
    /**
     * Method add record into groups list.
     *
     * @param record record for add.
     */
    void add(IRecord record);
    /**
     * Method get count of records in current group.
     *
     * @return Size.
     */
    int getSize();
    IGroup concat(IGroup group);
    List<IRecord> getRecords();
    Set<IValue> getValues();
}
