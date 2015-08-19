package aperf;

import myessentials.new_config.data.ConfigData;

@myessentials.new_config.Config
public class Config {
    @myessentials.new_config.Config.Instance
    public static ConfigData data;

    @myessentials.new_config.Config.Group
    public static class General {
        @myessentials.new_config.Config.Property(comment = "Localization file without file extension.\\nLoaded from config/aPerf/localization/ first, then from the jar, then finally will fallback to en_US if needed.")
        public static String Localization = "en_US";

        @myessentials.new_config.Config.Property(comment = "A list of modules to load. Use * to load all modules.")
        public static String[] LoadedModules = {"*"};
    }
}
