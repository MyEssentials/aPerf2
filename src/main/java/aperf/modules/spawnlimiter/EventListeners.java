package aperf.modules.spawnlimiter;

import aperf.api.util.EntityHelper;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkEvent;

import java.util.List;

public class EventListeners {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityJoinWorld(EntityJoinWorldEvent ev) {
        if (!(ev.entity instanceof EntityLivingBase))
            return;

        if (!SpawnLimiterModule.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void specialSpawn(LivingSpawnEvent.SpecialSpawn ev) {
        if (!(ev.entity instanceof EntityLivingBase))
            return;

        if (!SpawnLimiterModule.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void checkSpawn(LivingSpawnEvent.CheckSpawn ev) {
        if (!(ev.entity instanceof EntityLivingBase))
            return;

        if (!SpawnLimiterModule.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load ev) {
        Chunk chunk = ev.getChunk();
        World world = chunk.worldObj;

        for (List<?> list : chunk.entityLists) {
            if (list == null)
                continue;

            for (Object o : list) {
                if (o == null || !(o instanceof EntityLivingBase))
                    continue;

                EntityLivingBase e = (EntityLivingBase) o;
                if (!SpawnLimiterModule.findLimitAndCheck(e, world)) {
                    EntityHelper.removeEntity(e);
                }
            }
        }
    }
}
