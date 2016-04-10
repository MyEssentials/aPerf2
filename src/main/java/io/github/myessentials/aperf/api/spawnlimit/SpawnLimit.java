package io.github.myessentials.aperf.api.spawnlimit;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.core.filter.MultiFilter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.entity.Entity;

/**
 * Interface for all SpawnLimits to build off of
 */
public abstract class SpawnLimit {
    private Filter filter;
    private boolean enabled = true;

    /**
     * Returns the {@link SpawnLimitType} this {@link SpawnLimit} is
     * @return Returns an instance of the {@link SpawnLimitType} for this {@link SpawnLimit}
     */
    public abstract SpawnLimitType getType();

    /**
     * Checks if the {@link Entity} can spawn
     * @param e The {@link Entity} to check
     * @return If the {@link Entity} can spawn
     */
    public abstract boolean canSpawn(Entity e);

    /**
     * Serializes the values of this {@link SpawnLimit} to a {@link ConfigurationNode}
     * @param value The {@link ConfigurationNode} to serialize to
     * @throws ObjectMappingException
     */
    public abstract void serialize(ConfigurationNode value) throws ObjectMappingException;

    /**
     * Deserialize the values from the given {@link ConfigurationNode} into this {@link SpawnLimit}
     * @param value The {@link ConfigurationNode} to deserialize from
     * @throws ObjectMappingException
     */
    public abstract void deserialize(ConfigurationNode value) throws ObjectMappingException;

    /**
     * Checks if the {@link Entity} is a hit on the {@link Filter}
     * @param e The {@link Entity} to check
     * @return If the {@link Entity} is a hit
     */
    public final boolean hits(Entity e) {
        return filter != null && filter.hits(e);
    }

    /**
     * Returns if this {@link SpawnLimit} is enabled or not
     * @return The current enable state of this {@link SpawnLimit}
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enable state of this {@link SpawnLimit}
     * @param enabled The new enable state
     */
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Toggles the enable state of this {@link SpawnLimit}
     */
    public final void toggleEnabled() {
        setEnabled(!this.enabled);
    }

    /**
     * Returns the {@link Filter} that this SpawnLimit uses
     * @return The {@link Filter} that is being used
     */
    public final Filter getFilter() {
        return filter;
    }

    /**
     * Sets the {@link Filter} that this SpawnLimit uses
     * @param filter The {@link Filter} to use
     */
    public final void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * Adds or sets the {@link Filter} of this {@link SpawnLimit}
     *
     * If the current {@link Filter} is null, set it to the given {@link Filter}.
     * If the current {@link Filter} is a {@link MultiFilter}, add the given {@link Filter} to it.
     * If the current {@link Filter} is not a {@link MultiFilter}, make it a {@link MultiFilter} adding the old {@link Filter} and the given {@link Filter}.
     *
     * @param f The {@link Filter} to add
     */
    public final void addFilter(Filter f) {
        // Ignore if a null filter is given
        if (f == null) return;

        if (filter == null) {
            filter = f;
        } else if (filter instanceof MultiFilter) {
            ((MultiFilter) filter).addFilter(f);
        } else {
            Filter tmp = filter;
            filter = new MultiFilter();
            addFilter(tmp);
            addFilter(f);
        }
    }

    /**
     * Removes the given {@link Filter} from this {@link SpawnLimit}
     *
     * If current {@link Filter} is null, do nothing
     * If current {@link Filter} is the given {@link Filter}, set current {@link Filter} to null
     * If current {@link Filter} is a {@link MultiFilter} remove the {@link Filter} from it
     *
     * @param f The {@link Filter} to remove
     */
    public final void removeFilter(Filter f) {
        if (f == null || filter == null) return;

        if (filter == f) {
            filter = null;
        } else if (filter instanceof MultiFilter) {
            ((MultiFilter) f).removeFilter(f);
        }
    }

    @Override
    public String toString() {
        return String.format("%s(enabled=%s; filter=%s)", getType().getId(), isEnabled(), filter == null ? "None" : filter.toString());
    }
}
