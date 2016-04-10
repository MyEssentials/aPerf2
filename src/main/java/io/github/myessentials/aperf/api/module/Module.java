package io.github.myessentials.aperf.api.module;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public abstract class Module implements CatalogType {
    private boolean enabled = true;
    private final SimpleDispatcher commandDispatcher = new SimpleDispatcher();

    public abstract void load();

    public abstract void save();

    /**
     * Returns if this {@link Module} is currently enabled or not
     * @return The state of this {@link Module}
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enable state of this {@link Module}
     * @param enabled The new enable state
     */
    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the a {@link Text} version of the current enable state
     * @return A {@link Text} of the current enable state
     */
    public final Text getEnableText() {
        return isEnabled() ? Text.of(TextColors.GREEN, "Enabled") : Text.of(TextColors.RED, "Disabled");
    }

    /**
     * Returns the {@link SimpleDispatcher} associated with this {@link Module}'s commands
     * @return The {@link SimpleDispatcher} of this {@link Module}
     */
    public SimpleDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }
}
