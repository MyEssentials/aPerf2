package io.github.myessentials.aperf.core.limits;

import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitType;
import io.github.myessentials.aperf.api.spawnlimit.impl.CountLimit;
import org.spongepowered.api.entity.Entity;

/**
 * Limits by counting entities in a world
 */
public class WorldCount extends CountLimit {
    @Override
    public String getName() {
        return "WorldCount";
    }

    @Override
    public boolean canSpawn(Entity e) {
        return this.countEntities(e.getWorld()) < limit;
    }

    public static class Type implements SpawnLimitType {
        @Override
        public String getId() {
            return "WorldCount";
        }

        @Override
        public String getName() {
            return "WorldCount";
        }

        @Override
        public String desc() {
            return "Limits by counting entities in a world";
        }

        @Override
        public Class<? extends SpawnLimit> getSpawnLimitClass() {
            return WorldCount.class;
        }
    }
}
