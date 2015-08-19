package aperf.modules.spawnlimiter.cmd.creation;

import aperf.api.filter.IFilter;
import aperf.cmd.Commands;
import aperf.modules.spawnlimiter.util.SpawnLimitCreation;
import mypermissions.command.CommandResponse;
import mypermissions.command.annotation.Command;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class SpawnLimitFilterCommans extends Commands {
    @Command(name = "filter",
            permission = "aperf.cmd.module.entity.spawn.filter",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn filter <command>",
            alias = {"f"})
    public static CommandResponse spawnLimitFilterCommand(ICommandSender sender, List<String> args) {
        return CommandResponse.SEND_HELP_MESSAGE;
    }

    @Command(name = "add",
            permission = "aperf.cmd.module.entity.spawn.filter.add",
            parentName = "aperf.cmd.module.entity.spawn.filter",
            syntax = "/aperf entity spawn filter add <type> <options>",
            alias = {"a"})
    public static CommandResponse spawnLimitFilterAddCommand(ICommandSender sender, List<String> args) {
        // args: <type> <options>
        if (args.size() < 2) return CommandResponse.SEND_HELP_MESSAGE;
        SpawnLimitCreation.SpawnLimitCreationState state = SpawnLimitCreation.getCreationState(sender);
        IFilter filter = loadFilter(args.get(0), args.get(1));
        state.limit().addFilter(filter);

        return CommandResponse.DONE;
    }
}
