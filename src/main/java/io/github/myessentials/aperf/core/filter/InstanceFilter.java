package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import javax.annotation.Nonnull;

public class InstanceFilter extends StringFilter {
    private Class<?> clazz;

    @Override
    public String getName() {
        return "Instance";
    }

    @Override
    public boolean hits(Object o) {
        return o != null && clazz.isAssignableFrom(o.getClass());
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        // Deserialize the String
        super.deserialize(value);

        // Get Class<?> from String
        updateClazz();
    }

    @Override
    public void deserialize(String value) throws ObjectMappingException {
        // Deserialize the String
        super.deserialize(value);

        // Get Class<?> from String
        updateClazz();
    }

    private void updateClazz() throws ObjectMappingException {
        try {
            clazz = Class.forName(this.value);
        } catch (ClassNotFoundException e) {
            throw new ObjectMappingException(String.format("Failed to get class instance %s", this.value), e);
        }
    }

    public static class Type extends FilterType {
        @Override
        public String desc() {
            return "Catches if its a class/superclass";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return InstanceFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Instance";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Instance";
        }
    }
}
