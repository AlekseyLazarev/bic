package ru.alazarev.parser;

import java.util.Objects;

/**
 * Class ColumnValue
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Column {
    private int column;
    private String value;
    private boolean notEmpty;

    /**
     * Constructor.
     *
     * @param column Number of column.
     * @param value  Value of column.
     */
    public Column(int column, String value) {
        this.column = column;
        this.value = value.replace("\"", "");
        if (this.value.equals("")) {
            notEmpty = false;
        } else {
            notEmpty = true;
        }
    }

    /**
     * Check empty column or not.
     *
     * @return result of check
     */
    public boolean isNotEmpty() {
        return notEmpty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column that = (Column) o;
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
