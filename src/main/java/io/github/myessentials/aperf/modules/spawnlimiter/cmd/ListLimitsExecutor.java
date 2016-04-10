package io.github.myessentials.aperf.modules.spawnlimiter.cmd;

import com.google.common.collect.ImmutableMap;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.modules.spawnlimiter.SpawnLimiterModule;
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
import static org.spongepowered.api.text.TextTemplate.*;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;

class ListLimitsExecutor implements CommandExecutor {
    private static final TextTemplate titleTemplate = TextTemplate.of(Text.of("Spawn Limits"), "(", arg("enabled"), ")");
    private static final Text headerText = Text.of("# |Active|Type           |Filter");
    private static final TextTemplate limitLineTemplate = TextTemplate.of(arg("id"), "|", arg("active"), "|", arg("type"), "|", arg("filter"));

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        // Get PaginationService
        PaginationList.Builder builder = Sponge.getServiceManager()
                .provide(PaginationService.class)
                .orElseThrow(() -> new CommandException(Text.of("Failed to get Pagination service")))
                .builder();

        List<Text> contents = new ArrayList<>();
        List<SpawnLimit> limits = SpawnLimiterModule.instance.getLimits();
        SpawnLimit tmpLimit;

        // Build contents
        for (int i = 0; i < limits.size(); i++) {
            tmpLimit = limits.get(i);
            contents.add(limitLineTemplate.apply(ImmutableMap.of(
                    "id", Text.of(String.format("%-2s", i)),
                    "active", tmpLimit.isActive() ? Text.of(TextColors.GREEN, String.format("%-6s", "On")) : Text.of(TextColors.RED, String.format("%-6s", "Off")),
                    "type", Text.of(String.format("%-15s", tmpLimit.getType().getId())),
                    "filter", Text.of(tmpLimit.getFilter().toString())
            )).build());
        }

        // Build response
        builder
                .title(titleTemplate.apply(ImmutableMap.of(
                        "enabled", SpawnLimiterModule.instance.getEnableText()
                )).build())
                .header(headerText)
                .contents(contents)
                .sendTo(src);

        return CommandResult.success();
    }
}
