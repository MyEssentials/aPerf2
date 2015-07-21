package aperf.cmd;

import aperf.api.moduleLoader.ModuleContainer;
import aperf.subsystem.module.ModuleSubsystem;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import myessentials.utils.ChatUtils;
import net.minecraft.command.ICommandSender;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

public class ModuleCommands extends Commands {
    @CommandNode(
            name = "module",
            permission = "aperf.cmd.module",
            parentName = "aperf.cmd",
            alias = {"m"},
            completionKeys = {"aperfModuleCompletion"})
    public static void moduleCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            listModulesCommand(sender, args);
            return;
        }

        CommandManager.callSubFunctions(sender, args, "aperf.cmd.module", getLocal());
    }

    @CommandNode(
            name = "list",
            permission = "aperf.cmd.module.list",
            parentName = "aperf.cmd.module",
            alias = {"l"})
    public static void listModulesCommand(ICommandSender sender, List<String> args) {
        Set<String> moduleNames = ModuleSubsystem.Instance().getModuleNames();
        sendMessageBackToSender(sender, StringUtils.join(moduleNames, ", "));
    }

    @CommandNode(
            name = "toggle",
            permission = "aperf.cmd.module.toggle",
            parentName = "aperf.cmd.module",
            completionKeys = {"aperfModuleCompletion"})
    public static void toggleModuleCommand(ICommandSender sender, List<String> args) {
        if (args == null || args.size() <= 0) {
            return;
        }

        String modName = args.get(0);
        ModuleContainer<?> modContainer = ModuleSubsystem.Instance().getModuleFromName(modName);

        if (args.size() == 2) {
            boolean enabled = ChatUtils.equalsOn(args.get(1));
            modContainer.setEnabled(enabled);
        } else {
            modContainer.toggle();
        }
    }
}
