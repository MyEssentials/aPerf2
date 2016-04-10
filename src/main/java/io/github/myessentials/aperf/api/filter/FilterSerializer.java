package io.github.myessentials.aperf.api.filter;

import com.google.common.reflect.TypeToken;
import io.github.myessentials.aperf.api.grouper.GroupSupplier;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;

import java.util.Optional;

/**
 * A {@link TypeSerializer} for {@link Filter} instances.
 */
public class FilterSerializer implements TypeSerializer<Filter> {
    @Override
    public Filter deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        // Get filter type, if it exists
        String filterType = Optional.ofNullable(value.getNode("type"))
                .filter(node -> !node.isVirtual())
                .filter(node -> !node.getString().isEmpty())
                .orElseThrow(() -> new ObjectMappingException("Filter must specify type"))
                .getString();

        // Get the registration
        FilterType registration = Sponge.getRegistry().getType(FilterType.class, filterType)
                .orElseThrow(() -> new ObjectMappingException("Unknown filter type " + filterType));

        // Get the value node
        ConfigurationNode filterValueNode = value.getNode("value");

        // Deserialize and return the filter
        return registration
                .deserialize(filterValueNode)
                .orElseThrow(() -> new ObjectMappingException("Failed to create filter of type " + filterType));
    }

    @Override
    public void serialize(TypeToken<?> type, Filter obj, ConfigurationNode value) throws ObjectMappingException {
        // Set the type
        value.getNode("type").setValue(obj.getType().getId());

        // Get the value node
        ConfigurationNode valueNode = value.getNode("value");

        // Serialize the values
        obj.serialize(valueNode);
    }
}
