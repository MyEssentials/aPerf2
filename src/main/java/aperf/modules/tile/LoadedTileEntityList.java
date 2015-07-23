package aperf.modules.tile;

import aperf.api.HookRegistrar;
import aperf.api.hooks.IEntityListHook;
import aperf.util.EntityList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.List;

public class LoadedTileEntityList extends EntityList<TileEntity> {
    public LoadedTileEntityList(World world, Field overriddenField) {
        super(world, overriddenField);
    }

    @Override
    public void tick() {
        for (IEntityListHook<TileEntity> hook : hooks()) {
            hook.call(world, innerList);
        }
    }

    @Override
    protected boolean tickOverride() {
        return true;
    }

    private List<IEntityListHook<TileEntity>> hooks() {
        return HookRegistrar.Instance().getTileEntityListHookList();
    }
}
