package aperf.modules.spawnlimiter.config;

import myessentials.config.ConfigProperty;
import myessentials.config.ConfigTemplate;

public class Config extends ConfigTemplate {
    public static Config instance = new Config();

    public ConfigProperty<Boolean> OnJoinWorld = new ConfigProperty<Boolean>(
            "OnJoinWorld",
            "SpawnLimiter",
            "If spawn limits should be checked on world join event.",
            true
    );

    public ConfigProperty<Boolean> OnSpecialSpawn = new ConfigProperty<Boolean>(
            "OnSpecialSpawn",
            "SpawnLimiter",
            "If spawn limits should be checked on special spawn event. (Typically from mob spawners).",
            true
    );

    public ConfigProperty<Boolean> OnCheckSpawn = new ConfigProperty<Boolean>(
            "OnCheckSpawn",
            "SpawnLimiter",
            "If spawn limits should be checked on check spawn event.",
            true
    );

    public ConfigProperty<Boolean> OnChunkLoad = new ConfigProperty<Boolean>(
            "OnChunkLoad",
            "SpawnLimiter",
            "If spawn limits should be checked when a chunk loads.",
            true
    );
}
