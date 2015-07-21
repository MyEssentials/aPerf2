package aperf.modules.spawnlimiter;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkEvent;

public class EventListeners {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void entityJoinWorld(EntityJoinWorldEvent ev) {
        if (!(ev.entity instanceof EntityLivingBase) || ev.entity instanceof EntityPlayer)
            return;

        if (!SpawnLimiterModule.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void specialSpawn(LivingSpawnEvent.SpecialSpawn ev) {
        if (!SpawnLimiterModule.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void checkSpawn(LivingSpawnEvent.CheckSpawn ev) {
        if (!SpawnLimiterModule.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load ev) {
        // TODO Check on chunk-load?
    }

    @SubscribeEvent
    public void chunkUnload(ChunkEvent.Unload ev) {
    }
}
