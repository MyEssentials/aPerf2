package aperf.cmds;

import aperf.api.FilterRegistrar;
import aperf.api.exceptions.FilterException;
import aperf.api.filter.IFilter;
import aperf.exceptions.APerfCommandException;
import aperf.subsystem.module.ModuleSubsystem;
import myessentials.command.CommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands {

    /**
     * Calls the method to which the set of arguments corresponds to.
     */
    public static boolean callSubFunctions(ICommandSender sender, List<String> args, String callersPermNode) {
        List<String> subCommands = CommandManager.getSubCommandsList(callersPermNode);
        if (!args.isEmpty()) {
            for (String s : subCommands) {
                String name = CommandManager.commandNames.get(s);
                // Checking if name corresponds and if parent's
                if (name.equals(args.get(0)) && CommandManager.getParentPermNode(s).equals(callersPermNode)) {
                    CommandManager.commandCall(s, sender, args.subList(1, args.size()));
                    return true;
                }
            }
        }

        sendHelpMessage(sender, callersPermNode, null);
        return false;
    }

    /**
     * Sends the help message for the permission node with the arguments.
     */
    public static void sendHelpMessage(ICommandSender sender, String permBase, List<String> args) {
        String node;
        if (args == null || args.isEmpty()) {
            //If no arguments are provided then we check for the base permission
            node = permBase;
        } else {
            node = CommandManager.getPermissionNodeFromArgs(args, permBase);
        }


        String command = "/" + CommandManager.commandNames.get(permBase);

        if(args != null) {
            String prevNode = permBase;
            for (String s : args) {
                String t = CommandManager.getSubCommandNode(s, prevNode);
                if (t != null) {
                    command += " " + s;
                    prevNode = t;
                } else
                    break;
            }
        }

        sendMessageBackToSender(sender, command);
        List<String> scList = CommandManager.getSubCommandsList(node);
        if (scList == null || scList.isEmpty()) {
//            sendMessageBackToSender(sender, "   " + getLocal().getLocalization(node + ".help"));
        } else {
            List<String> nameList = new ArrayList<String>();
            for(String s : scList) {
                nameList.add(CommandManager.commandNames.get(s));
            }
            Collections.sort(nameList);
            for (String s : nameList) {
//                sendMessageBackToSender(sender, "   " + s + ": " + getLocal().getLocalization(CommandManager.getSubCommandNode(s, node) + ".help"));
            }
        }
    }

    public static void sendMessageBackToSender(ICommandSender sender, String message) {
        sender.addChatMessage(new ChatComponentText(message));
    }

    public static void populateCompletionMap() {
        List<String> populator = new ArrayList<String>();
        populator.addAll(ModuleSubsystem.Instance().getModuleNames());
        CommandManager.completionMap.put("aperfModuleCompletion", populator);
    }

    /**
     * Loads an {@link IFilter} throwing a command exception on error
     * @param name Name of IFilter to load
     * @param conf Config of IFilter
     * @return The created/loaded filter
     */
    public static IFilter loadFilter(String name, String conf) {
        try {
            return FilterRegistrar.INSTANCE.loadFilter("Multi", conf);
        } catch (FilterException.FilterNotFoundException e) {
            throw new APerfCommandException("Filter not found!", e);
        } catch (FilterException.FilterCreationException e) {
            throw new APerfCommandException("Filter failed to load!", e);
        } catch (FilterException.FilterLoadException e) {
            throw new APerfCommandException("Filter failed to load!", e);
        }
    }

    /**
     * Creates an {@link IFilter} throwing a command exception on error
     * @param name Name of IFilter to load
     * @return The created/loaded filter
     */
    public static IFilter loadGroup(String name) {
        try {
            return FilterRegistrar.INSTANCE.createGrouper(name);
        } catch (FilterException.FilterNotFoundException e) {
            throw new APerfCommandException("Group %s not found!", e, name);
        } catch (FilterException.FilterCreationException e) {
            throw new APerfCommandException("Group %s failed!", e, name);
        }
    }
}
