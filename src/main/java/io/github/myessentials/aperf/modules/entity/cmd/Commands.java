package io.github.myessentials.aperf.modules.entity.cmd;

import io.github.myessentials.aperf.api.filter.FilterRegistration;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class Commands {
    private static final CommandSpec listEntitiesCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module.entity.list")
            .arguments(
                    GenericArguments.catalogedElement(Text.of("group"), FilterRegistration.class),
                    GenericArguments.catalogedElement(Text.of("filter"), FilterRegistration.class),
                    GenericArguments.optional(GenericArguments.string(Text.of("filterConfig")))
            )
            .executor(new ListEntitiesExecutor())
            .build();

    public static void register(SimpleDispatcher dispatcher) {
        dispatcher.register(listEntitiesCommandSpec, "list", "l");
    }
}
