package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.api.filter.impl.AbstractFilter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nonnull;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that catches all
 */
public class AllFilter extends AbstractFilter {
    public AllFilter() {
    }

    @Override
    public boolean hits(Object o) {
        return true;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        // Unused
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        // Unused
    }

    @Override
    public void deserialize(String value) throws ObjectMappingException {
        // Unused
    }

    @Override
    public String getName() {
        return "All";
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Catches all";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return AllFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "All";
        }

        @Override
        @Nonnull
        public String getName() {
            return "All";
        }
    }
}
