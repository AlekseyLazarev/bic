package ru.alazarev.parser.entities.implementations;

import ru.alazarev.parser.entities.interfaces.IValue;

import java.util.Objects;

/**
 * Class ColumnValue
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Value implements IValue {
    private int column;
    private String value;
    private boolean notEmpty;

    /**
     * Constructor.
     *
     * @param column Number of column.
     * @param value  Value of column.
     */
    public Value(int column, String value) {
        this.column = column;
        this.value = value.replace("\"", "");
        notEmpty = !this.value.equals("");
    }

    @Override
    public boolean isNotEmpty() {
        return notEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Value that = (Value) o;
        return column == that.column &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, value);
    }

    @Override
    public String toString() {
        if (column == 0) return String.format("%12s", value);
        return String.format("%16s", value);
    }
}
