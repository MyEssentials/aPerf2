package aperf;

import myessentials.new_config.annotations.ConfigProperty;

public class Config {
    @ConfigProperty(name = "Localization", comment = "Localization file without file extension.\\nLoaded from config/aPerf/localization/ first, then from the jar, then finally will fallback to en_US if needed.")
    public static final String localization = "en_US";
}
