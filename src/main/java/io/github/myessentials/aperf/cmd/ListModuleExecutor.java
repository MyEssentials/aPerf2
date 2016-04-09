package io.github.myessentials.aperf.cmd;

import com.google.common.collect.ImmutableMap;
import io.github.myessentials.aperf.api.module.Module;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;

import java.util.List;
import java.util.stream.Collectors;

// TODO Cache the builder and refresh as needed?

public class ListModuleExecutor implements CommandExecutor {
    private static final TextTemplate template = TextTemplate.of(
            TextTemplate.arg("moduleName"), " - ", TextTemplate.arg("enableState")
    );

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        // Get builder
        PaginationList.Builder builder = Sponge.getServiceManager().provide(PaginationService.class)
                .orElseThrow(() -> new CommandException(Text.of("Failed to get pagination service")))
                .builder();

        // Build contents
        List<Text> contents = Sponge
                .getRegistry()
                .getAllOf(Module.class)
                .stream()
                .map(module -> template.apply(ImmutableMap.of(
                        "moduleName", Text.of(module.getId()),
                        "enableState", Text.of(module.isEnabled() ? "enabled" : "disabled")
                )).build())
                .collect(Collectors.toList());

        // Build builder stuffs and send
        builder
                .title(Text.of("Modules"))
                .padding(Text.of("-"))
                .contents(contents)
                .sendTo(src);

        return CommandResult.success();
    }
}
