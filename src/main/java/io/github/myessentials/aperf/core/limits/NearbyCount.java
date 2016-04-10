package io.github.myessentials.aperf.core.limits;

import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitType;
import io.github.myessentials.aperf.api.spawnlimit.impl.CountLimit;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;

import java.util.Optional;

public class NearbyCount extends CountLimit {
    /**
     * Holds the {@link NearbyCount} {@link SpawnLimitType} for convenience
     */
    public static final SpawnLimitType typeInstance = new Type();

    private int range = 0;

    @Override
    public SpawnLimitType getType() {
        return typeInstance;
    }

    @Override
    public boolean canSpawn(Entity e) {
        return e.getNearbyEntities(entity -> !EntityTypes.PLAYER.equals(entity.getType()) && this.hits(entity) && entity.getTransform().getPosition().distance(e.getTransform().getPosition()) <= range).size() < limit;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        value.getNode("limit").setValue(limit);
        value.getNode("range").setValue(range);
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        limit = Optional.ofNullable(value.getNode("limit"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Limit is required"))
                .getInt();

        range = Optional.ofNullable(value.getNode("range"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("Range is required"))
                .getInt();
    }

    public static class Type implements SpawnLimitType {
        @Override
        public String getId() {
            return "NearbyCount";
        }

        @Override
        public String getName() {
            return "NearbyCount";
        }

        @Override
        public String desc() {
            return "Limits by counting entities within a specific range of the entity";
        }

        @Override
        public Class<? extends SpawnLimit> getSpawnLimitClass() {
            return NearbyCount.class;
        }
    }
}
