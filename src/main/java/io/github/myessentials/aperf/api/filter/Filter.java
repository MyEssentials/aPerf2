package io.github.myessentials.aperf.api.filter;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

/**
 * Interface for all Filters to build off of
 */
public interface Filter {
    /**
     * Name of this Filter.
     * MUST be unique and the same as {@link FilterRegistration#getId()}!
     * @return
     */
    String getName();

    /**
     * Checks if o is a hit on the filter.
     * Filter should type check the given Object and return false if it can't check it!
     * @param o The Object to check
     * @return If the Object hits the Filter
     */
    boolean hits(Object o);

    /**
     * Returns what group o belongs to.
     * @param o The Object to check
     * @return The group the Object belongs to
     */
    String group(Object o);

    /**
     * Serializes the values of this {@link Filter} to a {@link ConfigurationNode}
     * @param value The {@link ConfigurationNode} to serialize to
     * @throws ObjectMappingException
     */
    void serialize(ConfigurationNode value) throws ObjectMappingException;

    /**
     * Deserializes the values from the given {@link ConfigurationNode} into this {@link Filter}
     * @param value The {@link ConfigurationNode} to deserialize from
     * @throws ObjectMappingException
     */
    void deserialize(ConfigurationNode value) throws ObjectMappingException;

    /**
     * Deserialized the value from the given String into this {@link Filter}
     * @param value The String to deserialize from
     * @throws ObjectMappingException
     */
    void deserialize(String value) throws ObjectMappingException;
}
