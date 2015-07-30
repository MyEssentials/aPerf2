package aperf;

import myessentials.new_config.annotations.ConfigGroup;
import myessentials.new_config.annotations.ConfigProperty;

public class Config {
    @ConfigGroup
    public static class General {
        @ConfigProperty(comment = "Localization file without file extension.\\nLoaded from config/aPerf/localization/ first, then from the jar, then finally will fallback to en_US if needed.")
        public static String Localization = "en_US";

        @ConfigProperty(comment = "A list of modules to load. Use * to load all modules.")
        public static String[] LoadedModules = {"*"};
    }
}
