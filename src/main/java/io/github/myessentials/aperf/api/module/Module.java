package io.github.myessentials.aperf.api.module;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.command.dispatcher.SimpleDispatcher;

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
     * Toggles the enable state of this {@link Module}
     */
    public final void toggleEnabled() {
        setEnabled(!this.enabled);
    }

    public SimpleDispatcher getCommandDispatcher() {
        return commandDispatcher;
    }
}
