package io.github.myessentials.aperf.api.filter.impl;

import io.github.myessentials.aperf.api.filter.Filter;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Optional;

/**
 * {@link Filter} to load an {@link Integer} value
 */
public abstract class IntFilter extends AbstractValueFilter<Integer> {
    @Override
    public void deserialize(String value) throws ObjectMappingException {
        this.value = Integer.valueOf(Optional.ofNullable(value)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("Value must be given")));
    }
}
