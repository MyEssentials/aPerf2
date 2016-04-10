package io.github.myessentials.aperf.modules.entity.cmd;

import com.google.common.collect.ImmutableMap;
import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.grouper.GroupSupplier;
import io.github.myessentials.aperf.api.grouper.Grouper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class ListEntitiesExecutor implements CommandExecutor {
    private static final TextTemplate listEntryTemplate = TextTemplate.of(
            TextTemplate.arg("prefix").color(TextColors.WHITE),
            TextTemplate.arg("name").color(TextColors.RED),
            TextColors.GREEN, " | ",
            TextTemplate.arg("count").color(TextColors.YELLOW)
    );

    @Override
    @Nonnull
    public CommandResult execute(@Nonnull CommandSource src, @Nonnull CommandContext args) throws CommandException {
        Filter group;
        Filter filter;

        // Get Group
        try {
            group = args
                    .<FilterType>getOne("group")
                    .orElseThrow(() -> new CommandException(Text.of("Group must be given")))
                    .getFilterClass()
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommandException(Text.of("Failed to get group"), e);
        }

        // Get filter config
        String conf = (String) args.getOne("filterConfig").orElse("");

        // Get filter
        try {
            filter = args
                    .<FilterType>getOne("filter")
                    .orElseThrow(() -> new CommandException(Text.of("Filter must be given")))
                    .deserialize(conf)
                    .orElseThrow(() -> new CommandException(Text.of("Failed to get filter")));
        } catch (ObjectMappingException e) {
            throw new CommandException(Text.of("Failed to get filter"), e);
        }

        // Construct and send the results as a paginated list
        sendResult(src, filter, group);

        return CommandResult.success();
    }

    private static void sendResult(CommandSource src, Filter filter, GroupSupplier group) throws CommandException {
        // Create grouper
        Grouper<Entity> grouper = new Grouper<>(filter, group);

        // Get pagination builder
        PaginationList.Builder builder = Sponge.getServiceManager().provide(PaginationService.class)
                .orElseThrow(() -> new CommandException(Text.of("Failed to get pagination service")))
                .builder();

        List<Text> contents = new ArrayList<>();

        // Loop over every world
        for (World world : Sponge.getServer().getWorlds()) {
            // Get the entities in the world
            Collection<Entity> entities = world.getEntities();

            // Proceed only if there are entities
            if (entities.size() <= 0) continue;

            int total = 0;
            List<Map.Entry<String, Integer>> groups = grouper.group(entities);

            // Count total number of entities
            for (Map.Entry<String, Integer> e : groups) {
                total += e.getValue();
            }

            if (total > 0) {
                contents.add(Text.of(world.getName(), " [", world.getUniqueId(), "] ", total, " ", total == 1 ? "entity" : "entities"));
                contents.addAll(getCountedList("   ", groups));
            }
        }

        builder
                .title(Text.of("Entities"))
                .padding(Text.of("="))
                .contents(contents)
                .sendTo(src);
    }

    private static List<Text> getCountedList(String prefix, List<Map.Entry<String, Integer>> counts) {
        Text prefixText = Text.of(prefix);
        return counts.stream().map(entry -> listEntryTemplate.apply(ImmutableMap.of(
                "prefix", prefixText,
                "name", Text.of(String.format("%-10s", entry.getKey())),
                "count", Text.of(entry.getValue())
        )).build()).collect(Collectors.toList());
    }
}
