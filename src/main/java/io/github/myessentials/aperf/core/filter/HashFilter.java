package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.filter.impl.IntFilter;

import javax.annotation.Nonnull;

/**
 * {@link io.github.myessentials.aperf.api.filter.Filter} that checks the Java hashcode
 */
public class HashFilter extends IntFilter {
    public static final FilterType typeInstance = new Type();

    @Override
    public FilterType getType() {
        return typeInstance;
    }

    @Override
    public boolean hits(Object o) {
        return o != null && System.identityHashCode(o) == value;
    }

    @Override
    public String group(Object o) {
        return o == null ? null : String.valueOf(System.identityHashCode(o));
    }

    public static class Type extends FilterType {
        @Override
        public String desc() {
            return "Checks the Java hashcode";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return HashFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Hash";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Hash";
        }
    }
}
