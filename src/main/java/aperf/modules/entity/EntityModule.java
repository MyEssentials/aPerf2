package aperf.modules.entity;

import aperf.APerf;
import aperf.Constants;
import aperf.api.HookRegistrar;
import aperf.modules.entity.cmd.EntityCommands;
import aperf.modules.entity.hooks.TickEntities;
import aperf.modules.tile.LoadedTileEntityList;
import aperf.proxy.LocalizationProxy;
import aperf.subsystem.module.APModule;
import aperf.api.moduleLoader.ModuleEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import myessentials.new_config.ConfigProcessor;
import mypermissions.command.CommandManager;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@APModule(name = "Entity")
public class EntityModule {
    private boolean isHookInstalled = false;

    @SubscribeEvent
    public void preInit(ModuleEvent.ModulePreInitEvent ev) {
        // Load Config
        ConfigProcessor.load(Config.class, new Configuration(new File(Constants.CONFIG_FOLDER, "entity.cfg")));

        // Register Event Listeners
        FMLCommonHandler.instance().bus().register(new GrouperEvents());
    }

    @SubscribeEvent
    public void serverStarting(ModuleEvent.ModuleServerInitEvent ev) {
        CommandManager.registerCommands(EntityCommands.class, "aperf.cmd", LocalizationProxy.getLocalization(), null);

//        hook();
    }

    @SubscribeEvent
    public void serverStopped(ModuleEvent.ModuleServerStoppedEvent ev) {
    }

    private void hook() {
        if (isHookInstalled) return;
        isHookInstalled = true;

        HookRegistrar.Instance().hookEntityList(new TickEntities());

        final Collection<World> worlds = Arrays.<World>asList(DimensionManager.getWorlds());

        synchronized (EntityModule.class) {
            for (World world : worlds) {
                installHook(world);
            }
        }
    }

    private void unhook() {
        if (!isHookInstalled) return;
        isHookInstalled = false;

        final Collection<World> worlds = Arrays.<World>asList(DimensionManager.getWorlds());

        synchronized (EntityModule.class) {
            for (World world : worlds) {
                uninstallHook(world);
            }
        }
    }

    private synchronized void installHook(World world) {
        try {
            Field field = World.class.getField("loadedEntityList");
            new LoadedEntityList(world, field);
            APerf.LOG.debug("Hooked into world {} (DimID: {})", world.getClass(), world.provider.dimensionId);
        } catch (Exception e) {
            APerf.LOG.fatal("Failed to hook world {} (DimID: {})", world.getClass(), world.provider.dimensionId);
            APerf.LOG.catching(e);
        }
    }

    private synchronized void uninstallHook(World world) {
        try {
            Field field = World.class.getField("loadedEntityList");
            Object o = field.get(world);
            if (o instanceof LoadedEntityList) {
                ((LoadedTileEntityList) o).unhook();
                APerf.LOG.debug("Unhooked from world {}", world.getClass());
            } else {
                APerf.LOG.fatal("Could not uninstall hook! Looks like another mod changed loadedEntityList in world {}!", world.getClass());
            }
        } catch(Exception e) {
            APerf.LOG.fatal("Could not uninstall hook for world {}!", world.getClass());
            APerf.LOG.catching(e);
        }
    }
}
