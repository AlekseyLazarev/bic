package ru.alazarev.parser.entities.implementations;

import ru.alazarev.parser.entities.interfaces.IRecord;
import ru.alazarev.parser.entities.interfaces.IValue;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class Entity
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Record implements IRecord {
    private List<IValue> columns = new LinkedList<>();

    /**
     * Constructor.
     *
     * @param strings Columns values.
     */
    public Record(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            this.columns.add(new Value(i, strings[i]));
        }
    }

    public List<IValue> getColumns() {
        return columns;
    }

    @Override
    public List<IValue> getNotEmptyValues() {
        return this.columns.stream().filter(IValue::isNotEmpty).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        for (int i = 0; i < this.columns.size(); i++) {
            if (!this.columns.get(i).equals(record.columns.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columns);
    }

    @Override
    public String toString() {
        return columns.toString();
    }
}
