package aperf.cmd;

import aperf.api.moduleLoader.ModuleContainer;
import aperf.subsystem.module.ModuleSubsystem;
import myessentials.chat.api.ChatManager;
import myessentials.utils.ChatUtils;
import mypermissions.command.api.CommandResponse;
import mypermissions.command.api.annotation.Command;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public class ModuleCommands extends Commands {
    @Command(
            name = "module",
            permission = "aperf.cmd.module",
            syntax = "/aperf module <command>",
            parentName = "aperf.cmd",
            alias = {"m"},
            completionKeys = {"aperfModuleCompletion"})
    public static CommandResponse moduleCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            listModulesCommand(sender, args);
        }

        return CommandResponse.DONE;
    }

    @Command(
            name = "list",
            permission = "aperf.cmd.module.list",
            parentName = "aperf.cmd.module",
            syntax = "/aperf module list",
            alias = {"l"})
    public static CommandResponse listModulesCommand(ICommandSender sender, List<String> args) {
        Set<String> moduleNames = ModuleSubsystem.Instance().getModuleNames();
        ChatManager.send(sender, new ChatComponentText(StringUtils.join(moduleNames, ", ")));

        return CommandResponse.DONE;
    }

    @Command(
            name = "toggle",
            permission = "aperf.cmd.module.toggle",
            parentName = "aperf.cmd.module",
            syntax = "/aperf module toggle <module> [enable]",
            completionKeys = {"aperfModuleCompletion"})
    public static CommandResponse toggleModuleCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            return CommandResponse.SEND_HELP_MESSAGE;
        }

        String modName = args.get(0);
        ModuleContainer<?> modContainer = ModuleSubsystem.Instance().getModuleFromName(modName);

        if (args.size() == 2) {
            boolean enabled = ChatUtils.equalsOn(args.get(1));
            modContainer.setEnabled(enabled);
        } else {
            modContainer.toggle();
        }

        return CommandResponse.DONE;
    }
}
