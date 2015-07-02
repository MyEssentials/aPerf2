package aperf.modules.entity.limits;

import aperf.api.spawnlimit.CountLimit;
import aperf.api.spawnlimit.SpawnLimit;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Limits by counting entities over the whole server
 */
@SpawnLimit(name = "ServerCount", desc = "Limits by counting entities over the whole server")
public class ServerCount extends CountLimit {
    @Override
    public boolean canSpawn(Entity e, World w) {
        int current = 0;
        for (WorldServer worldServer : MinecraftServer.getServer().worldServers) {
            current += countEntities(w.loadedEntityList);
        }
        return current < limit;
    }
}
