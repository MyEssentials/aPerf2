package io.github.myessentials.aperf.modules.entity;

import io.github.myessentials.aperf.api.module.Module;
import io.github.myessentials.aperf.modules.entity.cmd.Commands;

public class EntityModule extends Module {
    @Override
    public void load() {
        Commands.register(this.getCommandDispatcher());
    }

    @Override
    public void save() {
    }

    @Override
    public String getId() {
        return "Entity";
    }

    @Override
    public String getName() {
        return "Entity";
    }
}
