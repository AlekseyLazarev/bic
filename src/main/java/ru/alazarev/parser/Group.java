package ru.alazarev.parser;

import java.util.LinkedList;
import java.util.Objects;

/**
 * Class Group
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Group {
    private LinkedList<Entity> group = new LinkedList<>();

    /**
     * Method add Entity into groups list.
     *
     * @param entity Entity for add.
     */
    public void add(Entity entity) {
        this.group.add(entity);
    }

    /**
     * Method get list of entities size.
     *
     * @return Size of list.
     */
    public int getSize() {
        return group.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group1 = (Group) o;
        return Objects.equals(group, group1.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int num = 1;
        for (Entity entity : this.group) {
            sb.append("Строка ").append(num++).append(" ");
            sb.append(entity.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
