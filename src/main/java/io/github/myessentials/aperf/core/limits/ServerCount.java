package io.github.myessentials.aperf.core.limits;

import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitType;
import io.github.myessentials.aperf.api.spawnlimit.impl.CountLimit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.World;

public class ServerCount extends CountLimit {
    /**
     * Holds the {@link ServerCount} {@link SpawnLimitType} for convenience
     */
    public static final SpawnLimitType typeInstance = new Type();

    @Override
    public SpawnLimitType getType() {
        return typeInstance;
    }

    @Override
    public boolean canSpawn(Entity e) {
        int count = 0;

        for (World world : Sponge.getServer().getWorlds()) {
            count += this.countEntities(world);
        }

        return count < limit;
    }

    public static class Type implements SpawnLimitType {
        @Override
        public String getId() {
            return "ServerCount";
        }

        @Override
        public String getName() {
            return "ServerCount";
        }

        @Override
        public String desc() {
            return "Limits by counting entities in the server";
        }

        @Override
        public Class<? extends SpawnLimit> getSpawnLimitClass() {
            return ServerCount.class;
        }
    }
}
