package aperf.modules.entity;

import myessentials.config.ConfigProperty;
import myessentials.config.ConfigTemplate;

public class Config extends ConfigTemplate {
    public static Config instance = new Config();

    public ConfigProperty<Boolean> GroupItems = new ConfigProperty<Boolean>(
            "GroupItems",
            "EntityGrouper",
            "If items should be grouped together or not.",
            false
    );

    public ConfigProperty<Boolean> GroupExpOrbs = new ConfigProperty<Boolean>(
            "GroupExpOrbs",
            "EntityGrouper",
            "If Exp Orbs should be grouped together or not.",
            true
    );

    public ConfigProperty<Double> Range = new ConfigProperty<Double>(
            "Range",
            "EntityGrouper",
            "The range from the item/exp orb to group.",
            10d
    );

    public ConfigProperty<Integer> SkipTicks = new ConfigProperty<Integer>(
            "SkipTicks",
            "EntityGrouper",
            "The number of ticks to wait between each grouping.",
            20
    );

    public ConfigProperty<Boolean> EnableSlowing = new ConfigProperty<Boolean>(
            "EnableSlowing",
            "TickModifier",
            "If entities should be slowed down to help reduce lag.\\nIf enabled, some entities will not tick, thus may appear to lag to unsuspecting players.",
            true
    );
}
