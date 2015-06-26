package aperf.modules.entity.limiter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkEvent;

/**
 */
public class SpawnLimiterEvents {
    protected SpawnLimiterEvents() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent ev) {
    }

    @SubscribeEvent
    public void specialSpawn(LivingSpawnEvent.SpecialSpawn ev) {
    }

    @SubscribeEvent
    public void checkSpawn(LivingSpawnEvent.CheckSpawn ev) {
    }

    @SubscribeEvent
    public void chunkLoad(ChunkEvent.Load ev) {
    }

    @SubscribeEvent
    public void chunkUnload(ChunkEvent.Unload ev) {
    }
}
