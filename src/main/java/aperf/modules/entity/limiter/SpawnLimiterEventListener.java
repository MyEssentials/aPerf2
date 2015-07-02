package aperf.modules.entity.limiter;

import aperf.APerf;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkEvent;

/**
 */
public class SpawnLimiterEventListener {
    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent ev) {
        if (!(ev.entity instanceof EntityLivingBase) || ev.entity instanceof EntityPlayer)
            return;

        if (!SpawnLimits.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void specialSpawn(LivingSpawnEvent.SpecialSpawn ev) {
        if (!SpawnLimits.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void checkSpawn(LivingSpawnEvent.CheckSpawn ev) {
        if (!SpawnLimits.findLimitAndCheck((EntityLivingBase) ev.entity, ev.world)) {
            ev.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load ev) {
    }

    @SubscribeEvent
    public void chunkUnload(ChunkEvent.Unload ev) {
    }
}
