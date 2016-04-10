package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;

import javax.annotation.Nonnull;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that checks based on the Class name
 */
public class ClassFilter extends StringFilter {
    public static final FilterType typeInstance = new Type();

    @Override
    public FilterType getType() {
        return typeInstance;
    }

    @Override
    public boolean hits(Object o) {
        return this.value.equals(o.getClass().getName());
    }

    @Override
    public String group(Object o) {
        return o == null ? null : o.getClass().getName();
    }

    public static class Type extends FilterType {
        @Override
        public String desc() {
            return "Checks the Class name";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return ClassFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Class";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Class";
        }
    }
}
