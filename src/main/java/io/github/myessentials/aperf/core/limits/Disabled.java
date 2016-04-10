package io.github.myessentials.aperf.core.limits;

import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.Entity;

public class Disabled extends SpawnLimit {
    @Override
    public String getName() {
        return "Disabled";
    }

    @Override
    public boolean canSpawn(Entity e) {
        return false;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
    }

    public static class Type implements SpawnLimitType {
        @Override
        public String getId() {
            return "Disabled";
        }

        @Override
        public String getName() {
            return "Disabled";
        }

        @Override
        public String desc() {
            return "Disables all spawns";
        }

        @Override
        public Class<? extends SpawnLimit> getSpawnLimitClass() {
            return Disabled.class;
        }
    }
}
