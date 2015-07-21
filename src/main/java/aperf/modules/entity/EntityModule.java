package aperf.modules.entity;

import aperf.Constants;
import aperf.modules.entity.cmd.EntityCommands;
import aperf.subsystem.module.APModule;
import aperf.api.moduleLoader.ModuleEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import myessentials.command.CommandManager;

import java.io.File;

@APModule(name = "Entity")
public class EntityModule {
    private static File CONFIG_FOLDER;

    @SubscribeEvent
    public void preInit(ModuleEvent.ModulePreInitEvent ev) {
        // Load Config
        CONFIG_FOLDER = new File(Constants.CONFIG_FOLDER, "/entity");
        // ConfigProcessor.load(Config.class, new Configuration(new File(CONFIG_FOLDER, "entity.cfg")));

        // Register Event Listeners
        FMLCommonHandler.instance().bus().register(new GrouperEvents());
    }

    @SubscribeEvent
    public void serverStarting(ModuleEvent.ModuleServerInitEvent ev) {
        CommandManager.registerCommands(EntityCommands.class);
    }

    @SubscribeEvent
    public void serverStopped(ModuleEvent.ModuleServerStoppedEvent ev) {
    }
}
