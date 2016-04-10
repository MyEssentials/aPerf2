package io.github.myessentials.aperf.api.filter.impl;

import io.github.myessentials.aperf.api.filter.Filter;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

/**
 * {@link Filter} that implements a sensible defaults for many methods
 */
public abstract class AbstractFilter implements Filter {
    @Override
    public void deserialize(String value) throws ObjectMappingException {
        throw new ObjectMappingException("String loading not implemented for MultiFilter yet");
    }

    @Override
    public String group(Object o) {
        return null;
    }

    @Override
    public String toString() {
        return getType().getId();
    }
}
