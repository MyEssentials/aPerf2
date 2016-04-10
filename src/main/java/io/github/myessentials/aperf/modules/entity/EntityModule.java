package io.github.myessentials.aperf.modules.entity;

import io.github.myessentials.aperf.api.module.Module;
import io.github.myessentials.aperf.modules.entity.cmd.Commands;

import javax.annotation.Nonnull;

public class EntityModule extends Module {
    @Override
    public void load() {
        // Register commands
        Commands.register(this.getCommandDispatcher());
    }

    @Override
    public void save() {
    }

    @Override
    @Nonnull
    public String getId() {
        return "Entity";
    }

    @Override
    @Nonnull
    public String getName() {
        return "Entity";
    }
}
