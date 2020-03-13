package ru.alazarev.parser.utils;

import ru.alazarev.parser.entities.interfaces.IGroup;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class ParserUtils
 * <p>
 * Date: 13.03.2020
 *
 * @author a.lazarev
 */
public class ParserUtils {

    /**
     * Method sort groups by size and return sorted list of groups.
     *
     * @param uniqGroups Set of group.
     * @return Sorted List of Group.
     */
    public static List<IGroup> sortBySize(Set<IGroup> uniqGroups) {
        return uniqGroups.stream().sorted((o1, o2) -> Integer.compare(o2.getSize(), o1.getSize())).collect(Collectors.toList());
    }

    /**
     * Method returns the number of groups with size above border.
     *
     * @return number of groups.
     */
    public static long groupCounter(List<IGroup> uniqGroups, int border) {
        return uniqGroups.stream().filter(group -> group.getSize() > border).count();
    }
}
