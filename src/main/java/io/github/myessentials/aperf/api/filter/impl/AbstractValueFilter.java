package io.github.myessentials.aperf.api.filter.impl;

import io.github.myessentials.aperf.api.filter.Filter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Optional;

/**
 * {@link Filter} to load a single generic value
 * @param <T> The type of the value
 */
public abstract class AbstractValueFilter<T> extends AbstractFilter {
    protected T value;

    @Override
    public String toString() {
        return String.format("%s(%s)", getType().getId(), value);
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        value.setValue(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        this.value = (T) Optional.ofNullable(value)
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Value is required"))
                .getValue();
    }
}
