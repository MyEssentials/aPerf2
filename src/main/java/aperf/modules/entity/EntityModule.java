package aperf.modules.entity;

import aperf.modules.loader.APModule;
import aperf.moduleLoader.ModuleEvent;
import aperf.modules.entity.limiter.SpawnLimiter;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 */
@APModule(name = "Entity")
public class EntityModule {
    private SpawnLimiter spawnLimiter;

    @SubscribeEvent
    public void preInit(ModuleEvent.ModulePreInitEvent ev) {
        Config.load();
        spawnLimiter = new SpawnLimiter(ev);
    }

    @SubscribeEvent
    public void serverStopped(ModuleEvent.ModuleServerStoppedEvent ev) {
        Config.save();
    }
}
