package io.github.myessentials.aperf.modules.spawnlimiter;

import io.github.myessentials.aperf.APerf;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

public final class EventListeners {
    private SpawnLimiterModule module;

    EventListeners(SpawnLimiterModule spawnLimiterModule) {
        module = spawnLimiterModule;
    }

    @Listener(order = Order.EARLY)
    public void onSpawnEntity(SpawnEntityEvent ev, @First SpawnCause spawnCause) {
        if (!"*".equals(module.config.causesToCatch.get(0)) &&
                !module.config.causesToCatch.contains(spawnCause.getType().getId()))
            return;

        APerf.getLogger().debug("SpawnEntityEvent caught");
        ev.filterEntities(module::findLimitAndCheck);
    }
}
