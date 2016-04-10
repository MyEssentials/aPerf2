package io.github.myessentials.aperf.api.filter;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.CatalogType;

import java.util.Optional;

public abstract class FilterType implements CatalogType {
    /**
     * The description of the Filter
     * @return The description
     */
    public abstract String desc();

    /**
     * Returns the Filter class for this registration
     * @return The class
     */
    public abstract Class<? extends Filter> getFilterClass();

    public final Optional<Filter> deserialize(ConfigurationNode value) throws ObjectMappingException {
        Optional<Filter> filter;

        try {
            // Try to create the Filter
            filter = Optional.ofNullable(getFilterClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectMappingException("Failed to create filter of type " + getId(), e);
        }

        // Deserialize filter configuration
        filter
                .orElseThrow(() -> new ObjectMappingException("Failed to create filter of type " + getId()))
                .deserialize(value);

        return filter;
    }

    public final Optional<Filter> deserialize(String value) throws ObjectMappingException {
        Optional<Filter> filter;

        try {
            // Try to create the Filter
            filter = Optional.ofNullable(getFilterClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectMappingException("Failed to create filter of type " + getId(), e);
        }

        // Deserialize filter configuration
        filter
                .orElseThrow(() -> new ObjectMappingException("Failed to create filter of type " + getId()))
                .deserialize(value);

        // Return the filter
        return filter;
    }
}
