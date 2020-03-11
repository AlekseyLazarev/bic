package ru.alazarev.parser;

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
public class Entity {
    private List<Column> columns = new LinkedList<>();

    /**
     * Constructor.
     *
     * @param strings Columns values.
     */
    public Entity(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            this.columns.add(new Column(i, strings[i]));
        }
    }

    /**
     * Method returns not empty columns.
     *
     * @return List of not empty Column.
     */
    public List<Column> getNotEmptyColumns() {
        return this.columns.stream().filter(Column::isNotEmpty).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(columns, entity.columns);
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
