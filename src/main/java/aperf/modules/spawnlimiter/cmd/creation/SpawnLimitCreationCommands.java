package aperf.modules.spawnlimiter.cmd.creation;

import aperf.api.SpawnLimitRegistrar;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.cmd.Commands;
import aperf.exceptions.APerfCommandException;
import aperf.modules.spawnlimiter.config.LimitsConfig;
import aperf.modules.spawnlimiter.util.SpawnLimitCreation;
import myessentials.chat.api.ChatManager;
import mypermissions.command.api.CommandResponse;
import mypermissions.command.api.annotation.Command;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class SpawnLimitCreationCommands extends Commands {
    @Command(name = "create",
            permission = "aperf.cmd.module.entity.spawn.create",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn create <type> <options>",
            alias = {"c"})
    public static CommandResponse createSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <type> <options>
        if (args.size() < 2) return CommandResponse.SEND_HELP_MESSAGE;
        if (SpawnLimitCreation.creationStateMap.containsKey(sender)) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.alreadystarted"); // TODO: Is the name ok?!?
        }

        ISpawnLimit limit = SpawnLimitRegistrar.INSTANCE.loadSpawnLimit(args.get(0), args.get(1));
        SpawnLimitCreation.creationStateMap.put(sender, new SpawnLimitCreation.SpawnLimitCreationState(limit, true));

        return CommandResponse.DONE;
    }

    @Command(name = "remove",
            permission = "aperf.cmd.module.entity.spawn.remove",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn remove <id>",
            alias = {"r", "rm"})
    public static CommandResponse deleteSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <id>
        if (args.size() < 1) return CommandResponse.SEND_HELP_MESSAGE;
        int id = Integer.parseInt(args.get(0));
        if (id < 0 || id > LimitsConfig.Limits.size()) throw new APerfCommandException("aperf.spawn.notfound", args.get(0));
        LimitsConfig.Limits.remove(id);
        ChatManager.send(sender, "aperf.cmd.module.entity.spawn.delete.success");

        return CommandResponse.DONE;
    }

    @Command(name = "edit",
            permission = "aperf.cmd.module.entity.spawn.edit",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn edit <id>",
            alias = {"e"})
    public static CommandResponse editSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <id>
        if (args.size() < 1) return CommandResponse.SEND_HELP_MESSAGE;
        if (SpawnLimitCreation.creationStateMap.containsKey(sender)) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.alreadystarted"); // TODO: Is the name ok?!?
        }
        ISpawnLimit limit = LimitsConfig.Limits.get(Integer.parseInt(args.get(0)));
        if (limit == null) throw new APerfCommandException("aperf.spawn.notfound", args.get(0));
        SpawnLimitCreation.creationStateMap.put(sender, new SpawnLimitCreation.SpawnLimitCreationState(limit, false));

        return CommandResponse.DONE;
    }

    @Command(name = "save",
            permission = "aperf.cmd.module.entity.spawn.save",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn save",
            alias = {"s"})
    public static CommandResponse saveSpawnLimitCommand(ICommandSender sender, List<String> args) {
        SpawnLimitCreation.SpawnLimitCreationState state = SpawnLimitCreation.getCreationState(sender);
        // TODO Validate the ISpawnLimit?
        if (state.isNewLimit()) {
            LimitsConfig.Limits.add(state.limit());
        }
        LimitsConfig.save();
        SpawnLimitCreation.creationStateMap.remove(sender);

        return CommandResponse.DONE;
    }

    @Command(name = "discard",
            permission = "aperf.cmd.module.entity.spawn.discard",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn discard",
            alias = {"d"})
    public static CommandResponse discardSpawnLimitCommand(ICommandSender sender, List<String> args) {
        SpawnLimitCreation.SpawnLimitCreationState state = SpawnLimitCreation.getCreationState(sender);
        if (state.isNewLimit()) {
            SpawnLimitCreation.creationStateMap.remove(sender);
        } else {
            // TODO Handle discarding edits! Currently saving instead.
            saveSpawnLimitCommand(sender, args);
        }

        return CommandResponse.DONE;
    }
}
