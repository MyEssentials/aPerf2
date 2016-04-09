package io.github.myessentials.aperf.cmd;

import com.google.common.collect.ImmutableMap;
import io.github.myessentials.aperf.api.module.Module;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;

import java.util.Optional;

public class ToggleModuleExecutor implements CommandExecutor {
    private static final CommandException InvalidModuleException = new CommandException(Text.of("Invalid module"));
    private static final TextTemplate template = TextTemplate.of(
            TextTemplate.arg("moduleName"), " was ", TextTemplate.arg("enableState")
    );

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Module> module = args.getOne("module");
        Optional<Boolean> enabled = args.getOne("enable");

        if (enabled.isPresent()) {
            // If the enable argument was present, use the value to change the enabled state of the module
            module
                    .orElseThrow(() -> InvalidModuleException)
                    .setEnabled(enabled.get());
        } else {
            // Otherwise we just toggle
            module
                    .orElseThrow(() -> InvalidModuleException)
                    .toggleEnabled();
        }

        // Let the sender know what happened
        src.sendMessage(template.apply(ImmutableMap.of(
                "moduleName", Text.of(module.get().getId()),
                "enableState", Text.of((module.get().isEnabled() ? "enabled" : "disabled"))
        )).build());

        return CommandResult.success();
    }
}
