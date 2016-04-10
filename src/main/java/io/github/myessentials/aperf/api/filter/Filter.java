package io.github.myessentials.aperf.api.filter;

import io.github.myessentials.aperf.api.grouper.GroupSupplier;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

/**
 * Interface for all Filters to build off of.
 */
public interface Filter extends GroupSupplier {
    /**
     * Returns the {@link FilterType} this {@link Filter} is
     * @return Returns an instance of the {@link FilterType} for this {@link Filter}
     */
    FilterType getType();

    /**
     * Checks if o is a hit on this {@link Filter}.
     * Filter should type check the given Object and return false if it can't check it!
     * @param o The Object to check
     * @return If the Object hits the Filter
     */
    boolean hits(Object o);

    /**
     * Serialize the values of this {@link Filter} to a {@link ConfigurationNode}
     * @param value The {@link ConfigurationNode} to serialize to
     * @throws ObjectMappingException
     */
    void serialize(ConfigurationNode value) throws ObjectMappingException;

    /**
     * Deserialize the values from the given {@link ConfigurationNode} into this {@link Filter}
     * @param value The {@link ConfigurationNode} to deserialize from
     * @throws ObjectMappingException
     */
    void deserialize(ConfigurationNode value) throws ObjectMappingException;

    /**
     * Deserialize the value from the given String into this {@link Filter}
     * @param value The String to deserialize from
     * @throws ObjectMappingException
     */
    void deserialize(String value) throws ObjectMappingException;
}
