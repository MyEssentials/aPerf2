package aperf.cmds;

import myessentials.command.Command;
import myessentials.command.CommandNode;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class APerfCommand extends Commands {
    @Command(name = "aperf", permission = "aperf.cmd", alias = {"ap"})
    public static void aPerfCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            sendHelpMessage(sender, "aperf.cmd", null);
            return;
        }

        callSubFunctions(sender, args, "aperf.cmd");
    }

    @CommandNode(
            name = "help",
            permission = "aperf.cmd.help",
            parentName = "aperf.cmd",
            alias = {"?"})
    public static void aPerfHelpCommand(ICommandSender sender, List<String> args) {
        sendHelpMessage(sender, "aperf.cmd", null);
    }
}
