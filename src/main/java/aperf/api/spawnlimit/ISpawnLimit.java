package aperf.api.spawnlimit;

import aperf.api.filter.IFilter;
import aperf.api.filter.MultiFilter;
import com.google.gson.JsonElement;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Interface for all SpawnLimits to build off of
 */
public abstract class ISpawnLimit {
    private IFilter filter;
    private boolean enabled;

    /**
     * Checks if {@link Entity} e can spawn in {@link World} w
     * @param e The Entity to check
     * @param w The World to check
     * @return If the Entity can spawn
     */
    public abstract boolean canSpawn(Entity e, World w);

    public abstract void load(JsonElement json);

    public abstract void load(String configStr);

    public abstract JsonElement save();

    /**
     * Checks if the {@link Entity} is a hit on the {@link IFilter}
     * @param e The entity to check
     * @return If the entity is a hit
     */
    public final boolean hits(Entity e) {
        return filter != null && filter.hits(e);
    }

    public final IFilter getFilter() {
        return filter;
    }

    public final void setFilter(IFilter f) {
        this.filter = f;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final void toggle() {
        setEnabled(!enabled);
    }

    /**
     * Adds or sets the {@link IFilter} of this {@link ISpawnLimit}
     *
     * If the current {@link IFilter} is null, set it to the given {@link IFilter}.
     * If the current {@link IFilter} is a {@link MultiFilter}, add the given {@link IFilter} to it.
     * If the current {@link IFilter} is not a {@link MultiFilter}, make it a {@link MultiFilter} adding the old {@link IFilter} and the given {@link IFilter}.
     *
     * @param f The {@link IFilter} to add
     */
    public final void addFilter(IFilter f) {
        if (filter == null) {
            filter = f;
        } else if (filter instanceof MultiFilter) {
            ((MultiFilter) filter).addFilter(f);
        } else {
            IFilter temp = filter;
            filter = new MultiFilter();
            ((MultiFilter) filter).addFilter(temp);
            ((MultiFilter) filter).addFilter(f);
        }
    }

    /**
     * Removes the given {@link IFilter} from this {@link ISpawnLimit}
     *
     * If current {@link IFilter} is null, do nothing
     * If current {@link IFilter} is the given {@link IFilter}, set current {@link IFilter} to null
     * If current {@link IFilter} is a {@link MultiFilter} remove the {@link IFilter} from it
     *
     * @param f
     */
    public final void removeFilter(IFilter f) {
        if (filter == null) return;
        if (filter == f) {
            filter = null;
        } else if (filter instanceof MultiFilter) {
            ((MultiFilter) filter).removeFilter(f);
        }
    }
}
