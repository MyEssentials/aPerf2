package aperf.modules.tile;

import myessentials.config.ConfigProperty;
import myessentials.config.ConfigTemplate;

public class Config extends ConfigTemplate {
    public static Config instance = new Config();

    public ConfigProperty<Boolean> EnableSlowing = new ConfigProperty<Boolean>(
            "EnableSlowing",
            "TickModifier",
            "If tile entities should be slowed down to help reduce lag.\\nIf enabled, some tile entities will not tick, thus may appear to lag to unsuspecting players.",
            true
    );
}
