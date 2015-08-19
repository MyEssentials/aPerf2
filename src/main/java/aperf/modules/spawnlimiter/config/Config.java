package aperf.modules.spawnlimiter.config;

import myessentials.new_config.data.ConfigData;

@myessentials.new_config.Config
public class Config {
    @myessentials.new_config.Config.Instance
    public static ConfigData data;

    @myessentials.new_config.Config.Group
    public static class SpawnLimiter {
        @myessentials.new_config.Config.Property(comment = "If spawn limits should be checked on world join event.")
        public static final boolean OnJoinWorld = true;

        @myessentials.new_config.Config.Property(comment = "If spawn limits should be checked on special spawn event. (Typically from mob spawners).")
        public static final boolean OnSpecialSpawn = true;

        @myessentials.new_config.Config.Property(comment = "If spawn limits should be checked on check spawn event.")
        public static final boolean OnCheckSpawn = true;

        @myessentials.new_config.Config.Property(comment = "If spawn limits should be checked when a chunk loads.")
        public static final boolean OnChunkLoad = true;
    }
}
