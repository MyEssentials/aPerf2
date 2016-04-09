package io.github.myessentials.aperf.api.filter.impl;

import io.github.myessentials.aperf.api.filter.Filter;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Optional;

/**
 * {@link Filter} to load a {@link String} value
 */
public abstract class StringFilter extends AbstractValueFilter<String> {
    @Override
    public void deserialize(String value) throws ObjectMappingException {
        this.value = Optional.ofNullable(value)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("Value must be given"));
    }
}
