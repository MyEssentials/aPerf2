package aperf.cmd;

import myessentials.command.Command;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class APerfCommand extends Commands {
    @Command(name = "aperf", permission = "aperf.cmd", alias = {"ap"})
    public static void aPerfCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            aPerfHelpCommand(sender, args);
            return;
        }

        CommandManager.callSubFunctions(sender, args, "aperf.cmd", getLocal());
    }

    @CommandNode(
            name = "help",
            permission = "aperf.cmd.help",
            parentName = "aperf.cmd",
            alias = {"?"})
    public static void aPerfHelpCommand(ICommandSender sender, List<String> args) {
        CommandManager.sendHelpMessage(sender, "aperf.cmd", null, getLocal());
    }
}
