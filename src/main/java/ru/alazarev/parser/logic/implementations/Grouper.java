package ru.alazarev.parser.logic.implementations;

import ru.alazarev.parser.entities.implementations.Group;
import ru.alazarev.parser.entities.implementations.Record;
import ru.alazarev.parser.entities.interfaces.IGroup;
import ru.alazarev.parser.entities.interfaces.IRecord;
import ru.alazarev.parser.entities.interfaces.IValue;
import ru.alazarev.parser.logic.interfaces.IGrouper;

import java.util.*;

/**
 * Class Grouper
 * <p>
 * Date: 11.03.2020
 *
 * @author a.lazarev
 */
public class Grouper implements IGrouper {
    private String delimiter;
    private List<String> strings;
    private Map<IValue, IGroup> groupMap = new LinkedHashMap<>();

    public Grouper(List<String> strings, String delimiter) {
        this.delimiter = delimiter;
        this.strings = strings;
    }

    /**
     * Method creating records from List strings.
     *
     * @return Set uniq records.
     */
    private Set<IRecord> entitiesParse() {
        Set<IRecord> entities = new LinkedHashSet<>();
        for (String current : this.strings) {
            entities.add(new Record(current.split(this.delimiter)));
        }
        return entities;
    }

    /**
     * Method unites List of groups.
     *
     * @param groups List of groups.
     * @return united group.
     */
    private IGroup concatGroupsInMap(List<IGroup> groups) {
        IGroup result = groups.get(0);
        for (IGroup iGroup : groups) {
            result.concat(iGroup);
        }
        return result;
    }

    /**
     * Method get all groups from map with key from values.
     *
     * @param values List of keys.
     * @return List of groups.
     */
    private List<IGroup> getGroupsFromMap(List<IValue> values) {
        List<IGroup> result = new LinkedList<>();
        for (IValue cv : values) {
            if (this.groupMap.containsKey(cv)) result.add(this.groupMap.remove(cv));
        }
        deleteFromMap(result);
        return result.size() > 0 ? result : null;
    }

    /**
     * Method add group to map.
     *
     * @param group Group for add.
     */
    private void add(IGroup group) {
        for (IValue value : group.getValues()) {
            this.groupMap.put(value, group);
        }
    }

    /**
     * Delete all keys for list group from map.
     *
     * @param groups List group for delete.
     */
    private void deleteFromMap(List<IGroup> groups) {
        for (IGroup group : groups) {
            for (IValue value : group.getValues()) {
                this.groupMap.remove(value);
            }
        }
    }

    @Override
    public Set<IGroup> group() {
        for (IRecord record : entitiesParse()) {
            List<IGroup> groupsFromMap = getGroupsFromMap(record.getNotEmptyValues());
            IGroup current;
            if (groupsFromMap == null) {
                current = new Group();
            } else {
                current = concatGroupsInMap(groupsFromMap);
            }
            current.add(record);
            add(current);
        }
        return new HashSet<>(this.groupMap.values());
    }
}
