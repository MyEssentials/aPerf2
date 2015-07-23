package aperf.api;

import aperf.api.hooks.IEntityListHook;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class HookRegistrar {
    private static HookRegistrar Instance;

    private final List<IEntityListHook<Entity>> entityListHookList;
    private final List<IEntityListHook<TileEntity>> tileEntityListHookList;

    private HookRegistrar() {
        entityListHookList = new ArrayList<IEntityListHook<Entity>>();
        tileEntityListHookList = new ArrayList<IEntityListHook<TileEntity>>();
    }

    public static HookRegistrar Instance() {
        if (Instance == null) Instance = new HookRegistrar();
        return Instance;
    }

    //***** loadedEntityList Hooks *****//

    public void hookEntityList(IEntityListHook<Entity> hook) {
        entityListHookList.add(hook);
    }

    public void unhookEntityList(IEntityListHook<Entity> hook) {
        entityListHookList.remove(hook);
    }

    public List<IEntityListHook<Entity>> getEntityListHookList() {
        return entityListHookList;
    }

    //***** loadedTileEntityList Hooks *****//

    public void hookTileEntityList(IEntityListHook<TileEntity> hook) {
        tileEntityListHookList.add(hook);
    }

    public void unhookTileEntityList(IEntityListHook<TileEntity> hook) {
        tileEntityListHookList.remove(hook);
    }

    public List<IEntityListHook<TileEntity>> getTileEntityListHookList() {
        return tileEntityListHookList;
    }
}
