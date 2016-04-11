package io.github.myessentials.aperf.modules.tileentity.cmd;

import io.github.myessentials.aperf.api.filter.FilterType;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commands {
    private static final CommandSpec listTileEntitiesCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module.tileentity.list")
            .arguments(
                    GenericArguments.catalogedElement(Text.of("group"), FilterType.class),
                    GenericArguments.catalogedElement(Text.of("filter"), FilterType.class),
                    GenericArguments.optional(GenericArguments.string(Text.of("filterConfig")))
            )
            .executor(new ListTileEntitiesExecutor())
            .build();

    public static void register(SimpleDispatcher dispatcher) {
        dispatcher.register(listTileEntitiesCommandSpec, "list", "l");
    }

    private Commands() {
    }
}
