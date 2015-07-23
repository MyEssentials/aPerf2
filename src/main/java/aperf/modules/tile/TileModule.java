package aperf.modules.tile;

import aperf.APerf;
import aperf.subsystem.module.APModule;
import net.minecraft.world.World;

import java.lang.reflect.Field;

@APModule(name = "Tile")
public class TileModule {
    private synchronized void installHook(World world) {
        try {
            Field loadedTileEntityListField = World.class.getDeclaredField("loadedTileEntityList");
            new LoadedTileEntityList(world, loadedTileEntityListField);
            APerf.LOG.debug("Hooked into world {} (DimID: {})", world.getClass(), world.provider.dimensionId);
        } catch (Exception e) {
            APerf.LOG.fatal("Failed to hook world {} (DimID: {})", world.getClass(), world.provider.dimensionId);
            e.printStackTrace();
        }
    }

    private synchronized void uninstallHook(World world) {
        // TODO Uninstall hook?!?
    }
}
