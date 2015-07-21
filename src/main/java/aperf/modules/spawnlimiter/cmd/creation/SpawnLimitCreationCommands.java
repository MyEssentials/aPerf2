package aperf.modules.spawnlimiter.cmd.creation;

import aperf.api.SpawnLimitRegistrar;
import aperf.api.filter.IFilter;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.cmd.Commands;
import aperf.exceptions.APerfCommandException;
import aperf.modules.spawnlimiter.Config;
import aperf.modules.spawnlimiter.util.SpawnLimitCreation;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import net.minecraft.command.ICommandSender;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SpawnLimitCreationCommands extends Commands {
    @CommandNode(name = "create",
            permission = "aperf.cmd.module.entity.spawn.create",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"c"})
    public static void createSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <type> <options>
        if (args.size() < 2) {
            CommandManager.sendHelpMessage(sender, "aperf.cmd.module.entity.spawn.create", null, getLocal());
            return;
        }
        if (SpawnLimitCreation.creationStateMap.containsKey(sender)) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.alreadystarted"); // TODO: Is the name ok?!?
        }

        ISpawnLimit limit = SpawnLimitRegistrar.INSTANCE.loadSpawnLimit(args.get(0), args.get(1));
        SpawnLimitCreation.creationStateMap.put(sender, new SpawnLimitCreation.SpawnLimitCreationState(limit, true));
    }
    @CommandNode(name = "remove",
            permission = "aperf.cmd.module.entity.spawn.remove",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"r", "rm"})
    public static void deleteSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <id>
    }

    @CommandNode(name = "edit",
            permission = "aperf.cmd.module.entity.spawn.edit",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"e"})
    public static void editSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <id>
        if (args.size() < 1) {
            CommandManager.sendHelpMessage(sender, "aperf.cmd.module.entity.spawn.edit", null, getLocal());
            return;
        }
        if (SpawnLimitCreation.creationStateMap.containsKey(sender)) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.alreadystarted"); // TODO: Is the name ok?!?
        }
        ISpawnLimit limit = Config.Limits.get(Integer.parseInt(args.get(0)));
        if (limit == null) {
            throw new APerfCommandException("aperf.spawn.notfound", args.get(0));
        }
        SpawnLimitCreation.creationStateMap.put(sender, new SpawnLimitCreation.SpawnLimitCreationState(limit, false));
    }

    @CommandNode(name = "save",
            permission = "aperf.cmd.module.entity.spawn.save",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"s"})
    public static void saveSpawnLimitCommand(ICommandSender sender, List<String> args) {
        SpawnLimitCreation.SpawnLimitCreationState state = SpawnLimitCreation.getCreationState(sender);
        // TODO Validate the ISpawnLimit?
        if (state.isNewLimit()) {
            Config.Limits.add(state.limit());
        }
        Config.save();
        SpawnLimitCreation.creationStateMap.remove(sender);
    }

    @CommandNode(name = "discard",
            permission = "aperf.cmd.module.entity.spawn.discard",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"d"})
    public static void discardSpawnLimitCommand(ICommandSender sender, List<String> args) {
        SpawnLimitCreation.SpawnLimitCreationState state = SpawnLimitCreation.getCreationState(sender);
        if (state.isNewLimit()) {
            SpawnLimitCreation.creationStateMap.remove(sender);
        } else {
            // TODO Handle discarding edits! Currently saving instead.
            saveSpawnLimitCommand(sender, args);
        }
    }
}
