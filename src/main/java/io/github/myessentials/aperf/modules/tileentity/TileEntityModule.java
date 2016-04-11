package io.github.myessentials.aperf.modules.tileentity;

import io.github.myessentials.aperf.api.module.Module;
import io.github.myessentials.aperf.modules.tileentity.cmd.Commands;

import javax.annotation.Nonnull;

public class TileEntityModule extends Module {
    public static final TileEntityModule instance = new TileEntityModule();

    private TileEntityModule() {
    }

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
        return "TileEntity";
    }

    @Override
    @Nonnull
    public String getName() {
        return "TileEntity";
    }
}
