package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that checks the FULL class name
 */
public class LClassFilter extends StringFilter {
    @Override
    public boolean hits(Object o) {
        return this.value.equals(o.getClass().getSimpleName());
    }

    @Override
    public String group(Object o) {
        return o == null ? null : o.getClass().getSimpleName();
    }

    @Override
    public String getName() {
        return "LClass";
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Checks the full class name";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return LClassFilter.class;
        }

        @Override
        public String getId() {
            return "LClass";
        }

        @Override
        public String getName() {
            return "LClass";
        }
    }
}
