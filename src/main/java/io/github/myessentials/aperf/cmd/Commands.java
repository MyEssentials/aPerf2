package io.github.myessentials.aperf.cmd;

import io.github.myessentials.aperf.api.module.Module;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public final class Commands {
    private static CommandSpec listModuleCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module.list")
            .executor(new ListModuleExecutor())
            .build();

    private static CommandSpec toggleModuleCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module.toggle")
            .arguments(
                    GenericArguments.catalogedElement(Text.of("module"), Module.class),
                    GenericArguments.optional(GenericArguments.bool(Text.of("enable")))
            )
            .executor(new ToggleModuleExecutor())
            .build();

    private static CommandSpec moduleCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd.module")
            .child(listModuleCommandSpec, "list", "l")
            .child(toggleModuleCommandSpec, "toggle")
            .build();

    private static CommandSpec aPerfCommandSpec = CommandSpec.builder()
            .permission("aperf.cmd")
            .arguments(
                    GenericArguments.catalogedElement(Text.of("module"), Module.class),
                    GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("args")))
            )
            .executor((src, args) ->
                args.<Module>getOne("module")
                        .orElseThrow(() -> new CommandException(Text.of("Unknown/invalid module")))
                        .getCommandDispatcher()
                        .process(src, args.
                                <String>getOne("args")
                                .orElse(""))
            )
            .child(moduleCommandSpec, "module", "m")
            .build();

    public static void register(Object plugin, CommandManager manager) {
        manager.register(plugin, aPerfCommandSpec, "aperf", "ap");
    }
}
