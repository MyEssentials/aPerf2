package aperf.api.spawnlimit;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

/**
 * {@link ISpawnLimit} to limit based on a count
 */
public abstract class CountLimit extends ISpawnLimit {
    protected int limit = 0;

    @Override
    public void load(JsonElement json) {
        if (json.isJsonPrimitive()) {
            limit = json.getAsInt();
        }
    }

    @Override
    public void load(String configStr) {
        limit = Integer.parseInt(configStr);
    }

    @Override
    public JsonElement save() {
        return new JsonPrimitive(limit);
    }

    /**
     * Counts all the entities that match the Filter
     * @param entityList
     * @return
     */
    protected int countEntities(List<?> entityList) {
        int cnt = 0;
        for (int i = 0; i < entityList.size(); i++) {
            Entity e = (Entity) entityList.get(i);
            if (!(e instanceof EntityLivingBase) || e instanceof EntityPlayer) {
                continue;
            }

            // Check IFilter
            if (!this.hits(e)) {
                continue;
            }

            cnt++;
        }

        return cnt;
    }
}
