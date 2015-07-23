package aperf.api.hooks;

import net.minecraft.world.World;

import java.util.ArrayList;

public interface IEntityListHook<T> {
    void call(World world, ArrayList<T> list);
}
