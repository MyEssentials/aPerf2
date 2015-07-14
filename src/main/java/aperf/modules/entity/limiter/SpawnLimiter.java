package aperf.modules.entity.limiter;

import aperf.api.moduleLoader.ModuleEvent;
import aperf.modules.entity.Config;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 */
public class SpawnLimiter {
    private final SpawnLimiterEventListener events;

    public SpawnLimiter(ModuleEvent.ModulePreInitEvent ev) {
        SpawnLimitLoader.load(ev.getFMLEvent().getAsmData());
        Config.loadSpawnLimits();
        // Setup Event Listeners
        events = new SpawnLimiterEventListener();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }
}
