package aperf.modules.spawnlimiter.cmd.creation;

import aperf.api.filter.IFilter;
import aperf.cmd.Commands;
import aperf.modules.spawnlimiter.util.SpawnLimitCreation;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class SpawnLimitFilterCommans extends Commands {
    @CommandNode(name = "filter",
            permission = "aperf.cmd.module.entity.spawn.filter",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"f"})
    public static void spawnLimitFilterCommand(ICommandSender sender, List<String> args) {
        CommandManager.callSubFunctions(sender, args, "aperf.cmd.module.entity.spawn.filter", getLocal());
    }

    @CommandNode(name = "add",
            permission = "aperf.cmd.module.entity.spawn.filter.add",
            parentName = "aperf.cmd.module.entity.spawn.filter",
            alias = {"a"})
    public static void spawnLimitFilterAddCommand(ICommandSender sender, List<String> args) {
        // args: <type> <options>
        if (args.size() < 2) {
            CommandManager.sendHelpMessage(sender, "aperf.cmd.module.entity.spawn.filter.add", null, getLocal());
            return;
        }
        SpawnLimitCreation.SpawnLimitCreationState state = SpawnLimitCreation.getCreationState(sender);
        IFilter filter = loadFilter(args.get(0), args.get(1));
        state.limit().addFilter(filter);
    }
}
