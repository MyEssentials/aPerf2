package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;

import javax.annotation.Nonnull;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that checks the FULL class name
 */
public class LClassFilter extends StringFilter {
    public static final FilterType typeInstance = new Type();

    @Override
    public FilterType getType() {
        return typeInstance;
    }

    @Override
    public boolean hits(Object o) {
        return this.value.equalsIgnoreCase(o.getClass().getSimpleName());
    }

    @Override
    public String group(Object o) {
        return o == null ? null : o.getClass().getSimpleName();
    }

    public static class Type extends FilterType {
        @Override
        public String desc() {
            return "Checks the full class name";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return LClassFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "LClass";
        }

        @Override
        @Nonnull
        public String getName() {
            return "LClass";
        }
    }
}
