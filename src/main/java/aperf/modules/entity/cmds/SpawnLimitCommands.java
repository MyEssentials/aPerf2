package aperf.modules.entity.cmds;

import aperf.cmds.Commands;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class SpawnLimitCommands extends Commands {
    @CommandNode(
            name = "spawn",
            permission = "aperf.cmd.module.entity.spawn",
            parentName = "aperf.cmd.module.entity",
            alias = {"s"})
    public static void spawnLimitCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            CommandManager.sendHelpMessage(sender, "aperf.cmd.module.entity.spawn", null, getLocal());
            return;
        }

        CommandManager.callSubFunctions(sender, args, "aperf.cmd.module.entity.spawn", getLocal());
    }

    @CommandNode(name = "create",
            permission = "aperf.cmd.module.entity.spawn.create",
            parentName = "aperf.cmd.module.entity.spawn",
            alias = {"c"})
    public static void createSpawnLimitCommand(ICommandSender sender, List<String> args) {
    }
}
