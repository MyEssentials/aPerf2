package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that checks based on the Class name
 */
public class ClassFilter extends StringFilter {
    @Override
    public boolean hits(Object o) {
        return this.value.equals(o.getClass().getName());
    }

    @Override
    public String group(Object o) {
        return o == null ? null : o.getClass().getName();
    }

    @Override
    public String getName() {
        return "Class";
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Checks the Class name";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return ClassFilter.class;
        }

        @Override
        public String getId() {
            return "Class";
        }

        @Override
        public String getName() {
            return "Class";
        }
    }
}
