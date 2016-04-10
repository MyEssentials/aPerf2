package io.github.myessentials.aperf.modules.spawnlimiter.cmd;

import com.google.common.collect.ImmutableMap;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.modules.spawnlimiter.SpawnLimiterModule;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import static org.spongepowered.api.text.TextTemplate.*;

import java.util.List;

class ToggleLimitExecutor implements CommandExecutor {
    private static final TextTemplate responseTemplate = TextTemplate.of(arg("limitID"), " was ", arg("activeState"));
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        // Get id argument
        int limitID = args.
                <Integer>getOne("id")
                .orElseThrow(() -> new CommandException(Text.of("ID must be given")));

        // Get all limits
        List<SpawnLimit> limits = SpawnLimiterModule.instance.getLimits();

        // Check that limit is within the limits
        if (limitID >= limits.size()) throw new CommandException(Text.of("No limit with given ID"));

        // Get limit
        SpawnLimit limit = limits.get(limitID);

        // Set active state
        limit.setActive(args.<Boolean>getOne("enable").orElse(!limit.isActive()));

        // Send response
        src.sendMessage(responseTemplate.apply(ImmutableMap.of(
                "limitID", Text.of(limitID),
                "activeState", Text.of(limit.isActive() ? "enabled" : "disabled")
        )).build());

        return CommandResult.success();
    }
}
