package io.github.myessentials.aperf.api.spawnlimit.impl;

import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.util.Optional;

/**
 * {@link SpawnLimit} to limit based on a count
 */
public abstract class CountLimit extends SpawnLimit {
    protected int limit = 0;

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        value.setValue(limit);
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        limit = Optional.ofNullable(value)
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Value is required"))
                .getInt();
    }

    protected int countEntities(EntityUniverse entityUniverse) {
        return entityUniverse
                .getEntities(entity -> !EntityTypes.PLAYER.equals(entity.getType()) && this.hits(entity))
                .size();
    }
}
