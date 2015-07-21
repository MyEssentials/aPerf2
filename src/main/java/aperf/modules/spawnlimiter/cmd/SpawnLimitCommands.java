package aperf.modules.spawnlimiter.cmd;

import aperf.api.SpawnLimitRegistrar;
import aperf.api.filter.IFilter;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.SpawnLimitChatComponent;
import aperf.cmd.Commands;
import aperf.exceptions.APerfCommandException;
import aperf.modules.spawnlimiter.Config;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import myessentials.utils.ChatUtils;
import net.minecraft.command.ICommandSender;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SpawnLimitCommands extends Commands {
    @CommandNode(
            name = "spawn",
            permission = "aperf.cmd.module.entity.spawn",
            parentName = "aperf.cmd.module.entity",
            alias = {"s"})
    public static void spawnLimitCommand(ICommandSender sender, List<String> args) {
        CommandManager.callSubFunctions(sender, args, "aperf.cmd.module.entity.spawn", getLocal());
    }

    @CommandNode(
            name = "list",
            permission = "aperf.cmd.module.entity.spawn.list",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"l"})
    public static void listSpawnLimitsCommand(ICommandSender sender, List<String> args) {
        sendMessageBackToSender(sender, String.format("%3s|%6s|%46s", "#", "Active", "Type"));
        sendMessageBackToSender(sender, "-----------------------------------------------------");
        if (Config.Limits.size() > 0) {
            ISpawnLimit limit;
            for (int id = 0; id < Config.Limits.size(); id++) {
                limit = Config.Limits.get(id);
                sender.addChatMessage(new SpawnLimitChatComponent(id, limit));
            }
        } else {
            sendMessageBackToSender(sender, "You don't have any spawn limits created yet!");
            sendMessageBackToSender(sender, "To create a spawn limit, call /aperf entity spawn create <type> <options>");
        }
        sendMessageBackToSender(sender, "-----------------------------------------------------");
    }

    @CommandNode(name = "toggle",
            permission = "aperf.cmd.module.entity.spawn.toggle",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"t", "enable"})
    public static void toggleSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <id> [enable]
        ISpawnLimit limit = null;
        if (args == null || args.size() < 1) {
            if (creationStateMap.containsKey(sender)) {
                limit = creationStateMap.get(sender).limit;
            } else {
                CommandManager.sendHelpMessage(sender, "aperf.cmd.module.entity.spawn.toggle", null, getLocal());
                return;
            }
        } else {
            limit = Config.Limits.get(Integer.parseInt(args.get(0)));
        }
        if (limit == null) {
            throw new APerfCommandException("aperf.spawn.notfound", args == null ? "Unknown" : args.get(0));
        }
        if (args != null && args.size() >= 2) {
            limit.setEnabled(ChatUtils.equalsOn(args.get(1)));
        } else {
            limit.toggle();
        }
        Config.save();
    }

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
        if (creationStateMap.containsKey(sender)) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.alreadystarted"); // TODO: Is the name ok?!?
        }

        ISpawnLimit limit = SpawnLimitRegistrar.INSTANCE.loadSpawnLimit(args.get(0), args.get(1));
        creationStateMap.put(sender, new SpawnLimitCreationState(limit, true));
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
        if (creationStateMap.containsKey(sender)) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.alreadystarted"); // TODO: Is the name ok?!?
        }
        ISpawnLimit limit = Config.Limits.get(Integer.parseInt(args.get(0)));
        if (limit == null) {
            throw new APerfCommandException("aperf.spawn.notfound", args.get(0));
        }
        creationStateMap.put(sender, new SpawnLimitCreationState(limit, false));
    }

    @CommandNode(name = "save",
            permission = "aperf.cmd.module.entity.spawn.save",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"s"})
    public static void saveSpawnLimitCommand(ICommandSender sender, List<String> args) {
        SpawnLimitCreationState state = getCreationState(sender);
        // TODO Validate the ISpawnLimit?
        if (state.newLimit) {
            Config.Limits.add(state.limit);
        }
        Config.save();
        creationStateMap.remove(sender);
    }

    @CommandNode(name = "discard",
            permission = "aperf.cmd.module.entity.spawn.discard",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"d"})
    public static void discardSpawnLimitCommand(ICommandSender sender, List<String> args) {
        SpawnLimitCreationState state = getCreationState(sender);
        if (state.newLimit) {
            creationStateMap.remove(sender);
        } else {
            // TODO Handle discarding edits! Currently saving instead.
            saveSpawnLimitCommand(sender, args);
        }
    }

    public static class SpawnLimitFilterCommands extends Commands {
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
            SpawnLimitCreationState state = getCreationState(sender);
            IFilter filter = loadFilter(args.get(0), args.get(1));
            state.limit.addFilter(filter);
        }
    }

    private static Map<ICommandSender, SpawnLimitCreationState> creationStateMap = new WeakHashMap<ICommandSender, SpawnLimitCreationState>();

    private static class SpawnLimitCreationState {
        private ISpawnLimit limit;
        private boolean newLimit;

        public SpawnLimitCreationState(ISpawnLimit limit, boolean newLimit) {
            this.limit = limit;
            this.newLimit = newLimit;
        }
    }

    private static SpawnLimitCreationState getCreationState(ICommandSender sender) {
        SpawnLimitCreationState state = creationStateMap.get(sender);
        if (state == null) {
            throw new APerfCommandException("aperf.cmd.module.entity.spawn.notstarted"); // TODO: Is the name ok?!?
        }
        return state;
    }
}
