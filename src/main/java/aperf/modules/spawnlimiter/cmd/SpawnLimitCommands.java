package aperf.modules.spawnlimiter.cmd;

import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.SpawnLimitChatComponent;
import aperf.cmd.Commands;
import aperf.exceptions.APerfCommandException;
import aperf.modules.spawnlimiter.config.LimitsConfig;
import myessentials.chat.api.ChatManager;
import myessentials.utils.ChatUtils;
import mypermissions.command.api.CommandResponse;
import mypermissions.command.api.annotation.Command;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class SpawnLimitCommands extends Commands {
    @Command(
            name = "spawn",
            permission = "aperf.cmd.module.entity.spawn",
            parentName = "aperf.cmd.module.entity",
            syntax = "/aperf entity spawn <command>",
            alias = {"s"})
    public static CommandResponse spawnLimitCommand(ICommandSender sender, List<String> args) {
        return CommandResponse.SEND_HELP_MESSAGE;
    }

    @Command(
            name = "list",
            permission = "aperf.cmd.module.entity.spawn.list",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn list",
            alias = {"l"})
    public static CommandResponse listSpawnLimitsCommand(ICommandSender sender, List<String> args) {
        ChatManager.send(sender, new ChatComponentText(" # |Active|          Type          "));
        ChatManager.send(sender, new ChatComponentText("-----------------------------------------------------"));
        if (LimitsConfig.Limits.size() > 0) {
            ISpawnLimit limit;
            for (int id = 0; id < LimitsConfig.Limits.size(); id++) {
                limit = LimitsConfig.Limits.get(id);
                sender.addChatMessage(new SpawnLimitChatComponent(id, limit));
            }
        } else {
            ChatManager.send(sender, new ChatComponentText("You don't have any spawn limits created yet!"));
            ChatManager.send(sender, new ChatComponentText("To create a spawn limit, call /aperf entity spawn create <type> <options>"));
        }
        ChatManager.send(sender, new ChatComponentText("-----------------------------------------------------"));
        return CommandResponse.DONE;
    }

    @Command(name = "toggle",
            permission = "aperf.cmd.module.entity.spawn.toggle",
            parentName = "aperf.cmd.module.entity.spawn",
            syntax = "/aperf entity spawn toggle <id> [enable]",
            alias = {"t", "enable"})
    public static CommandResponse toggleSpawnLimitCommand(ICommandSender sender, List<String> args) {
        // args: <id> [enable]
        ISpawnLimit limit = null;
        if (args == null || args.size() < 1) return CommandResponse.SEND_HELP_MESSAGE;

        limit = LimitsConfig.Limits.get(Integer.parseInt(args.get(0)));
        if (limit == null) {
            throw new APerfCommandException("aperf.spawn.notfound", args.get(0));
        }
        if (args.size() >= 2) {
            limit.setEnabled(ChatUtils.equalsOn(args.get(1)));
        } else {
            limit.toggle();
        }
        LimitsConfig.save();
        return CommandResponse.DONE;
    }
}
