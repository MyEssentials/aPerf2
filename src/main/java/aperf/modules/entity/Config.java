package aperf.modules.entity;

import myessentials.new_config.annotations.ConfigProperty;
import myessentials.new_config.annotations.ConfigGroup;

public class Config {
    @ConfigGroup
    public static class EntityGrouper {
        @ConfigProperty(comment = "If items should be grouped together or not.")
        public static final boolean GroupItems = true;

        @ConfigProperty(comment = "If Exp Orbs should be grouped together or not.")
        public static final boolean GroupExpOrbs = true;

        @ConfigProperty(comment = "The range from the item/exp orb to group.")
        public static final double Range = 1.5;

        @ConfigProperty(comment = "The number of ticks to wait between each grouping.")
        public static final int SkipTicks = 20;
    }
}
