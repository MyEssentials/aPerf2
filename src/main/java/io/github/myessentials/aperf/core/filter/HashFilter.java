package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.api.filter.impl.IntFilter;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that checks the Java hashcode
 */
public class HashFilter extends IntFilter {
    @Override
    public boolean hits(Object o) {
        return o != null && System.identityHashCode(o) == value;
    }

    @Override
    public String group(Object o) {
        return o == null ? null : String.valueOf(System.identityHashCode(o));
    }

    @Override
    public String getName() {
        return "Hash";
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Checks the Java hashcode";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return HashFilter.class;
        }

        @Override
        public String getId() {
            return "Hash";
        }

        @Override
        public String getName() {
            return "Hash";
        }
    }
}
