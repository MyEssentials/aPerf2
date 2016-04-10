package io.github.myessentials.aperf.core.filter;

import com.google.common.reflect.TypeToken;
import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.api.filter.impl.AbstractFilter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MultiFilter extends AbstractFilter {
    private boolean allRequired = true;
    private List<Filter> filters = new ArrayList<>();

    @Override
    public String getName() {
        return "Multi";
    }

    @Override
    public boolean hits(Object o) {
        for (Filter filter : filters) {
            boolean hits = filter.hits(o);
            if (hits && !allRequired) {
                return true;
            } else if (!hits && allRequired) {
                return false;
            }
        }

        return allRequired;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        value.getNode("allRequired").setValue(allRequired);
        value.getNode("filters").setValue(filters);
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        // Are all sub-filters required?
        Optional.ofNullable(value.getNode("allRequired"))
                .filter(node -> !node.isVirtual())
                .ifPresent(node -> allRequired = node.getBoolean());

        // Get the sub-filters
        filters = Optional.ofNullable(value.getNode("filters"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("No filters given"))
                .getList(TypeToken.of(Filter.class));
    }

    @Override
    public String toString() {
        return String.format("Multi(allRequired=%s; filters=%s)", allRequired, filters);
    }

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void removeFilter(Filter filter) {
        if (!filters.remove(filter)) {
            filters.stream()
                    .filter(subFilter -> subFilter instanceof MultiFilter)
                    .forEach(subFilter -> ((MultiFilter) subFilter).removeFilter(filter));
        }
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Makes multiple filters act as one";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return MultiFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Multi";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Multi";
        }
    }
}
