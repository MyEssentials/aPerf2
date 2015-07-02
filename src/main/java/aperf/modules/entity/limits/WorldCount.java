package aperf.modules.entity.limits;

import aperf.api.spawnlimit.CountLimit;
import aperf.api.spawnlimit.SpawnLimit;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Limits by counting entities in a world
 */
@SpawnLimit(name = "ChunkCount", desc = "Limits by counting entities in a world")
public class WorldCount extends CountLimit {
    @Override
    public boolean canSpawn(Entity e, World w) {
        return this.countEntities(w.loadedEntityList) < limit;
    }
}
