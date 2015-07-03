package aperf.modules.entity.limiter;

import aperf.api.spawnlimit.ISpawnLimit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class SpawnLimits {
    public static final List<ISpawnLimit> Limits = new ArrayList<ISpawnLimit>();

    public static boolean findLimitAndCheck(EntityLivingBase entity, World world) {
        for (ISpawnLimit limit : Limits) {
            if (!limit.hits(entity))
                continue;

            if (!limit.canSpawn(entity, world))
                return false;
        }

        return true;
    }
}
