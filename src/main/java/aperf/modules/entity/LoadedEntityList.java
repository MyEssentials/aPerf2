package aperf.modules.entity;

import aperf.api.HookRegistrar;
import aperf.api.hooks.IEntityListHook;
import aperf.util.EntityList;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.List;

public class LoadedEntityList extends EntityList<Entity> {
    public LoadedEntityList(World world, Field overriddenField) {
        super(world, overriddenField);
    }

    @Override
    public void tick() {
        for (IEntityListHook<Entity> hook : hooks()) {
            hook.call(world, innerList);
        }
    }

    @Override
    protected boolean tickOverride() {
        return Config.instance.EnableSlowing.get();
    }

    private List<IEntityListHook<Entity>> hooks() {
        return HookRegistrar.Instance().getEntityListHookList();
    }
}
