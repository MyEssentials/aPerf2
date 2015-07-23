package aperf.modules.tile;

import aperf.APerf;
import aperf.api.moduleLoader.ModuleEvent;
import aperf.subsystem.module.APModule;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

@APModule(name = "Tile")
public class TileModule {
    private boolean isHookInstalled = false;

    @SubscribeEvent
    public void serverStarting(ModuleEvent.ModuleServerInitEvent ev) {
    }

    public void hook() {
        if (isHookInstalled) return;
        isHookInstalled = true;

        final Collection<World> worlds = Arrays.<World>asList(DimensionManager.getWorlds());

        synchronized (APerf.class) {
            for (World world : worlds) {
                installHook(world);
            }
        }
    }

    public void unhook() {
        if (!isHookInstalled) return;
        isHookInstalled = false;

        final Collection<World> worlds = Arrays.<World>asList(DimensionManager.getWorlds());

        synchronized (APerf.class) {
            for (World world : worlds) {
                uninstallHook(world);
            }
        }
    }

    private synchronized void installHook(World world) {
        try {
            Field loadedTileEntityListField = World.class.getDeclaredField("loadedTileEntityList");
            new LoadedTileEntityList(world, loadedTileEntityListField);
            APerf.LOG.debug("Hooked into world {} (DimID: {})", world.getClass(), world.provider.dimensionId);
        } catch (Exception e) {
            APerf.LOG.fatal("Failed to hook world {} (DimID: {})", world.getClass(), world.provider.dimensionId);
            APerf.LOG.catching(e);
        }
    }

    private synchronized void uninstallHook(World world) {
        try {
            Field loadedTileEntityListField = World.class.getDeclaredField("loadedTileEntityList");
            Object o = loadedTileEntityListField.get(world);
            if (o instanceof LoadedTileEntityList) {
                ((LoadedTileEntityList) o).unhook();
                APerf.LOG.debug("Unhooked from world {}", world.getClass());
            } else {
                APerf.LOG.fatal("Could not uninstall hook! Looks like another mod changed loadedTileEntityList in world {}!", world.getClass());
            }
        } catch (Exception e) {
            APerf.LOG.fatal("Could not uninstall hook for world {}!", world.getClass());
            APerf.LOG.catching(e);
        }
    }
}
