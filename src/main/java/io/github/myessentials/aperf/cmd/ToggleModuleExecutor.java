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

import javax.annotation.Nonnull;
import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class ToggleModuleExecutor implements CommandExecutor {
    private static final TextTemplate template = TextTemplate.of(
            TextTemplate.arg("moduleName"), " was ", TextTemplate.arg("enableState")
    );

    @Override
    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        Optional<Module> module = args.getOne("module");
        Optional<Boolean> enabled = args.getOne("enable");

        // Set enable state, or toggle when no "enable" arg given
        module
                .orElseThrow(() -> new CommandException(Text.of("Invalid module")))
                .setEnabled(enabled.orElse(!module.get().isEnabled()));

        // Let the sender know what happened
        src.sendMessage(template.apply(ImmutableMap.of(
                "moduleName", Text.of(module.get().getId()),
                "enableState", module.get().getEnableText()
        )).build());

        return CommandResult.success();
    }
}
