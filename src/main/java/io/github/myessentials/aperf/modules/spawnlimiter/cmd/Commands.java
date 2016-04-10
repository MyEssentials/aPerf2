package io.github.myessentials.aperf.modules.spawnlimiter.cmd;

import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public final class Commands {
    private static final CommandSpec listLimitsCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module.spawnlimiter.list")
            .executor(new ListLimitsExecutor())
            .build();

    private static final CommandSpec toggleLimitCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module.spawnlimiter.toggle")
            .arguments(
                    GenericArguments.integer(Text.of("id")),
                    GenericArguments.optional(GenericArguments.bool(Text.of("enable")))
            )
            .executor(new ToggleLimitExecutor())
            .build();

    public static void register(SimpleDispatcher dispatcher) {
        dispatcher.register(listLimitsCommandSpec, "list", "l");
        dispatcher.register(toggleLimitCommandSpec, "toggle");
    }

    private Commands() {
    }
}
