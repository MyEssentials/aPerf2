package aperf.modules.spawnlimiter.limits;

import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.SpawnLimit;
import com.google.gson.JsonElement;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Disables all spawns for the filter
 */
@SpawnLimit(name = "Disabled", desc = "Disables all spawns")
public class Disabled extends ISpawnLimit {
    @Override
    public boolean canSpawn(Entity e, World w) {
        return false;
    }

    @Override
    public void load(JsonElement json) {
    }

    @Override
    public void load(String configStr) {
    }

    @Override
    public JsonElement save() {
        return null;
    }
}
