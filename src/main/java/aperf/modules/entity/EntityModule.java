package aperf.modules.entity;

import aperf.modules.entity.cmd.EntityCommands;
import aperf.subsystem.module.APModule;
import aperf.api.moduleLoader.ModuleEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import myessentials.command.CommandManager;

@APModule(name = "Entity")
public class EntityModule {
    @SubscribeEvent
    public void preInit(ModuleEvent.ModulePreInitEvent ev) {
        Config.load();
    }

    @SubscribeEvent
    public void serverStarting(ModuleEvent.ModuleServerInitEvent ev) {
        CommandManager.registerCommands(EntityCommands.class);
    }

    @SubscribeEvent
    public void serverStopped(ModuleEvent.ModuleServerStoppedEvent ev) {
        Config.save();
    }
}
