package ru.alazarev.parser.entities.implementations;

import ru.alazarev.parser.entities.interfaces.IGroup;
import ru.alazarev.parser.entities.interfaces.IRecord;
import ru.alazarev.parser.entities.interfaces.IValue;

import java.util.*;

/**
 * Class Group
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Group implements IGroup {
    private LinkedList<IRecord> groupedRec = new LinkedList<>();
    private Set<IValue> values = new HashSet<>();

    @Override
    public void add(IRecord record) {
        this.groupedRec.add(record);
        this.values.addAll(record.getNotEmptyValues());
    }

    @Override
    public int getSize() {
        return this.groupedRec.size();
    }

    public IGroup concat(IGroup group) {
        Set<IRecord> resultRecords = new HashSet<>(this.groupedRec);
        resultRecords.addAll(group.getRecords());
        this.groupedRec = new LinkedList<>(resultRecords);
        return this;
    }

    @Override
    public List<IRecord> getRecords() {
        return this.groupedRec;
    }

    @Override
    public Set<IValue> getValues() {
        return this.values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group1 = (Group) o;
        for (int i = 0; i < this.groupedRec.size(); i++) {
            if (!this.groupedRec.get(i).equals(group1.groupedRec.get(i))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupedRec);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int num = 1;
        for (IRecord record : this.groupedRec) {
            sb.append("Строка ").append(num++).append(" ");
            sb.append(record.toString());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
