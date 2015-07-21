package aperf.modules.spawnlimiter.limits;

import aperf.api.spawnlimit.CountLimit;
import aperf.api.spawnlimit.SpawnLimit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

/**
 * Limits by counting entities in a chunk and around it
 */
@SpawnLimit(name = "ChunkCount", desc = "Limits by counting entities in a chunk and around it")
public class ChunkCount extends CountLimit {
    private int range = 0;

    @Override
    public boolean canSpawn(Entity e, World w) {
        int current = 0;
        int cx = (int) e.posX >> 4;
        int cz = (int) e.posZ >> 4;
        IChunkProvider cp = w.getChunkProvider();

        for (int z = cz - range; z <= cz+range; z++) {
            for (int x = cx - range; x <= cx+range; x++) {
                Chunk chunk = cp.chunkExists(x, z) ? cp.provideChunk(x, z) : null;

                if (chunk != null && chunk.entityLists != null) {
                    for (int i = 0; i < chunk.entityLists.length; i++) {
                        current += countEntities(chunk.entityLists[i]);
                    }
                }
            }
        }

        return current < limit;
    }

    @Override
    public void load(JsonElement json) {
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            limit = jsonObject.getAsJsonPrimitive("limit").getAsInt();
            range = jsonObject.getAsJsonPrimitive("range").getAsInt();
        }
    }

    @Override
    public void load(String configStr) {
        String[] parts = configStr.split(",");
        limit = Integer.parseInt(parts[0]);
        range= Integer.parseInt(parts[1]);
    }

    @Override
    public JsonElement save() {
        JsonObject ret = new JsonObject();
        ret.addProperty("limit", limit);
        ret.addProperty("range", range);
        return ret;
    }
}
