package io.github.myessentials.aperf.api.grouper;

import io.github.myessentials.aperf.api.filter.Filter;

import java.util.*;

public final class Grouper<T> {
    private final Filter filter;
    private final Filter groupFilter;

    public Grouper(Filter filter, Filter groupFilter) {
        this.filter = filter;
        this.groupFilter = groupFilter;
    }

    public List<Map.Entry<String, Integer>> group(Iterable<T> list, Comparator<Map.Entry<String, Integer>> comparator) {
        Map<String, Integer> counts = new HashMap<>();

        // If filter or group filter is null, return early
        if (filter == null || groupFilter == null) return new ArrayList<>(counts.entrySet());

        // Loop over all the objects
        for (T o : list) {
            // Check if the object hits the filter
            if (!filter.hits(o)) continue;

            // Get the group from the groupFilter
            String group = groupFilter.group(o);

            // Ignore the entity of the group is null
            if (group == null) continue;

            // Get the previous count, if there is one
            Integer prev = counts.get(group);

            // Update the count, or start a new count
            counts.put(group, prev == null ? 1 : prev + 1);
        }

        // Make the entries a List
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(counts.entrySet());

        // Sort the entries with the comparator
        Collections.sort(sorted, comparator);

        // Return the sorted List
        return sorted;
    }

    public List<Map.Entry<String, Integer>> group(Iterable<T> list) {
        return group(list, new DefaultGrouperComparator());
    }
}
