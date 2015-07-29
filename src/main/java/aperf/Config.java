package aperf;

import myessentials.new_config.annotations.ConfigGroup;
import myessentials.new_config.annotations.ConfigProperty;

public class Config {
    @ConfigGroup
    public static class General {
        @ConfigProperty(name = "Localization", comment = "Localization file without file extension.\\nLoaded from config/aPerf/localization/ first, then from the jar, then finally will fallback to en_US if needed.")
        public static String localization = "en_US";

        @ConfigProperty(name = "LoadedModules", comment = "A list of modules to load")
        public static String[] loadedModules = {"Entity", "Packet", "SpawnLimiter", "Tile"};
    }
}
