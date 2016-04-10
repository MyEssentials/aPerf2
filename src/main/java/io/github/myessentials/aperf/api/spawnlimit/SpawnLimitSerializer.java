package io.github.myessentials.aperf.api.spawnlimit;

import com.google.common.reflect.TypeToken;
import io.github.myessentials.aperf.api.filter.Filter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;

import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class SpawnLimitSerializer implements TypeSerializer<SpawnLimit> {
    @Override
    public SpawnLimit deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        // Get the type, if it exists
        String limitType = Optional.ofNullable(value.getNode("type"))
                .filter(node -> !node.isVirtual())
                .filter(node -> !"".equals(node.getString()))
                .orElseThrow(() -> new ObjectMappingException("Spawn limit must specify type"))
                .getString();

        // Get the registration
        SpawnLimitType registration = Sponge.getRegistry().getType(SpawnLimitType.class, limitType)
                .orElseThrow(() -> new ObjectMappingException("Unknown spawn limit type " + limitType));

        // Hold the SpawnLimit
        Optional<SpawnLimit> limit;

        try {
            // Try to create the SpawnLimit
            limit = Optional.ofNullable(registration.getSpawnLimitClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ObjectMappingException("Failed to create spawn limit of type " + limitType);
        }

        // Get the value node
        ConfigurationNode valueNode = value.getNode("value");

        // Deserialize spawn limits config
        limit
                .orElseThrow(() -> new ObjectMappingException("Failed to create spawn limit of type " + limitType))
                .deserialize(valueNode);

        // Set the filter
        limit.get().setFilter(Optional.ofNullable(value.getNode("filter"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Spawn limit must specify filter"))
                .getValue(TypeToken.of(Filter.class)));

        // Return the spawn limit
        return limit.get();
    }

    @Override
    public void serialize(TypeToken<?> type, SpawnLimit obj, ConfigurationNode value) throws ObjectMappingException {
        // Set the type
        value.getNode("type").setValue(obj.getName());

        // Store the filter
        value.getNode("filter").setValue(TypeToken.of(Filter.class), obj.getFilter());

        // Get the value node
        ConfigurationNode valueNode = value.getNode("value");

        // Serialize the values
        obj.serialize(valueNode);
    }
}
