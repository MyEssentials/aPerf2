package aperf.modules.spawnlimiter.cmd;

import aperf.api.SpawnLimitRegistrar;
import aperf.api.filter.IFilter;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.SpawnLimitChatComponent;
import aperf.cmd.Commands;
import aperf.exceptions.APerfCommandException;
import aperf.exceptions.APerfWrongUsageException;
import aperf.modules.spawnlimiter.Config;
import aperf.modules.spawnlimiter.util.SpawnLimitCreation;
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
        if (args == null || args.size() < 1) throw new APerfWrongUsageException("aperf.cmd.module.entity.spawn.toggle.help");

        limit = Config.Limits.get(Integer.parseInt(args.get(0)));
        if (limit == null) {
            throw new APerfCommandException("aperf.spawn.notfound", args.get(0));
        }
        if (args.size() >= 2) {
            limit.setEnabled(ChatUtils.equalsOn(args.get(1)));
        } else {
            limit.toggle();
        }
        Config.save();
    }
}
