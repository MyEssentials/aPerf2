package aperf.api.grouper;

import aperf.api.filter.IFilter;

import java.util.*;

// TODO Find a better name for this?
public class Grouper<T> {
    private IFilter filter;
    private IFilter groupFilter;

    public Grouper(IFilter filter, IFilter groupFilter) {
        this.filter = filter;
        this.groupFilter = groupFilter;
    }

    public List<Map.Entry<String, Integer>> group(List<T> list, Comparator<Map.Entry<String, Integer>> comparator) {
        Map<String, Integer> counts = new HashMap<String, Integer>();

        for (T o : list) {
            if (filter != null && !filter.hits(o)) continue;
            String group = null;
            if (groupFilter != null) group = groupFilter.group(o);
            if (group == null) continue;
            Integer prev = counts.get(group);
            counts.put(group, prev == null ? 1 : prev + 1);
        }

        List<Map.Entry<String, Integer>> sorted = new ArrayList<Map.Entry<String, Integer>>(counts.entrySet());

        Collections.sort(sorted, comparator);

        return sorted;
    }

    public List<Map.Entry<String, Integer>> group(List<T> list) {
        return group(list, new DefaultGrouperComparator());
    }
}
