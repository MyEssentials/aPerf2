package aperf.modules.tile;

public class Config {
    @myessentials.new_config.Config.Group
    public static class TickModifier {
        @myessentials.new_config.Config.Property(comment = "If tile entities should be slowed down to help reduce lag.\\nIf enabled, some tile entities will not tick, thus may appear to lag to unsuspecting players.")
        public static final boolean EnableSlowing = true;
    }
}
