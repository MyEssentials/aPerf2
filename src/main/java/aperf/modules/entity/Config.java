package aperf.modules.entity;

import myessentials.new_config.data.ConfigData;

@myessentials.new_config.Config
public class Config {
    @myessentials.new_config.Config.Instance
    public static ConfigData data;

    @myessentials.new_config.Config.Group
    public static class EntityGrouper {
        @myessentials.new_config.Config.Property(comment = "If items should be grouped together or not.")
        public static final boolean GroupItems = false;

        @myessentials.new_config.Config.Property(comment = "If Exp Orbs should be grouped together or not.")
        public static final boolean GroupExpOrbs = true;

        @myessentials.new_config.Config.Property(comment = "The range from the item/exp orb to group.")
        public static final double Range = 10;

        @myessentials.new_config.Config.Property(comment = "The number of ticks to wait between each grouping.")
        public static final int SkipTicks = 20;
    }

    @myessentials.new_config.Config.Group
    public static class TickModifier {
        @myessentials.new_config.Config.Property(comment = "If entities should be slowed down to help reduce lag.\\nIf enabled, some entities will not tick, thus may appear to lag to unsuspecting players.")
        public static final boolean EnableSlowing = true;
    }
}
