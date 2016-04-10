package io.github.myessentials.aperf.modules.spawnlimiter;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.TypeToken;
import io.github.myessentials.aperf.APerf;
import io.github.myessentials.aperf.api.module.Module;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.util.ConfigUtil;
import io.github.myessentials.aperf.modules.spawnlimiter.cmd.Commands;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;

import java.util.ArrayList;
import java.util.List;

public final class SpawnLimiterModule extends Module {
    public static final SpawnLimiterModule instance = new SpawnLimiterModule();

    public final Config config = new Config();

    private ConfigurationLoader<? extends ConfigurationNode> spawnLimitsLoader;
    private ConfigurationNode spawnLimitsRoot;

    private final List<SpawnLimit> limits = new ArrayList<>();

    private SpawnLimiterModule() {
    }

    @Override
    public void load() {
        // Load config
        try {
            spawnLimitsLoader = ConfigUtil.get(APerf.getConfigDir().resolve("spawnLimits.json"))
                    .orElseThrow(() -> new RuntimeException("Failed to load configuration for SpawnLimiterModule"));
            spawnLimitsRoot = spawnLimitsLoader.load();
            limits.addAll(spawnLimitsRoot.getList(TypeToken.of(SpawnLimit.class)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Register event listeners
        Sponge.getEventManager().registerListeners(APerf.instance, new EventListeners(this));

        // Register commands
        Commands.register(this.getCommandDispatcher());
    }

    @Override
    public void save() {
        try {
            spawnLimitsRoot.setValue(limits);
            spawnLimitsLoader.save(spawnLimitsRoot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "SpawnLimiter";
    }

    @Override
    public String getName() {
        return "SpawnLimiter";
    }

    public List<SpawnLimit> getLimits() {
        return ImmutableList.copyOf(limits);
    }

    boolean findLimitAndCheck(Entity entity) {
        APerf.getLogger().debug("Checking {} in world {} ({}) at ({}, {}, {})", entity.getType(), entity.getWorld().getUniqueId(), entity.getWorld().getName(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());

        // Ignore players
        if (EntityTypes.PLAYER.equals(entity.getType())) {
            APerf.getLogger().debug("Ignoring players");
            return true;
        }

        for(SpawnLimit limit : limits) {
            if (!limit.isActive()) {
                APerf.getLogger().debug("{}: disabled", limit.getType().getId());
                continue;
            }

            if (!limit.hits(entity)) {
                APerf.getLogger().debug("{}: filter doesn't get a hit", limit.getType().getId());
                continue;
            }

            if (!limit.canSpawn(entity)) {
                APerf.getLogger().debug("{}: Stopped spawning", limit.getType().getId());
                return false;
            }
        }

        APerf.getLogger().debug("Allowing spawning");
        return true;
    }
}
