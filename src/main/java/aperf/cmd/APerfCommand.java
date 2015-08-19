package aperf.cmd;

import mypermissions.command.CommandResponse;
import mypermissions.command.annotation.Command;
import net.minecraft.command.ICommandSender;

import java.util.List;

public class APerfCommand extends Commands {
    @Command(name = "aperf",
            permission = "aperf.cmd",
            alias = {"ap"},
            syntax = "/aperf <command>")
    public static CommandResponse aPerfCommand(ICommandSender sender, List<String> args) {
        return CommandResponse.SEND_HELP_MESSAGE;
    }

    @Command(
            name = "help",
            permission = "aperf.cmd.help",
            syntax = "/aperf help",
            parentName = "aperf.cmd",
            alias = {"?"})
    public static CommandResponse aPerfHelpCommand(ICommandSender sender, List<String> args) {
        return CommandResponse.SEND_HELP_MESSAGE;
    }
}
