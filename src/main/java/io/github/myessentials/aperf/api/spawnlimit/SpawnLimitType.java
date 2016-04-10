package io.github.myessentials.aperf.api.spawnlimit;

import org.spongepowered.api.CatalogType;

public interface SpawnLimitType extends CatalogType {
    /**
     * The description of the Filter
     * @return The description
     */
    String desc();

    /**
     * Returns the Filter class for this registration
     * @return The class
     */
    Class<? extends SpawnLimit> getSpawnLimitClass();
}
