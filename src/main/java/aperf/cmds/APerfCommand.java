package aperf.cmds;

import mytown.core.command.Command;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class APerfCommand extends Commands {
    @Command(name = "aperf", permission = "aperf.cmd", alias = {"ap"})
    public static void aPerfCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            return;
        }

        callSubFunctions(sender, args, "aperf.cmd");
    }
}
