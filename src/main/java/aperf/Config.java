package aperf;

import myessentials.config.ConfigProperty;
import myessentials.config.ConfigTemplate;


public class Config extends ConfigTemplate {
    public static final Config instance = new Config();

    public ConfigProperty<String> localization = new ConfigProperty<String>(
            "Localization",
            "general",
            "Localization file without file extension.\\nLoaded from config/aPerf/localization/ first, then from the jar, then finally will fallback to en_US if needed.",
            "en_US"
    );

    private static String[] loadedModulesDefault = {"*"};

    public ConfigProperty<String[]> loadedModules = new ConfigProperty<String[]>(
            "LoadedModules",
            "general",
            "A list of modules to load. Use * to load all modules.",
            loadedModulesDefault
    );
}
