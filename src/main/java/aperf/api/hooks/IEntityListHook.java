package aperf.api.hooks;

import net.minecraft.world.World;

import java.util.List;

public interface IEntityListHook<T> {
    void call(World world, List<T> list);
}
