package aperf.cmds;

import aperf.api.FilterRegistrar;
import aperf.api.exceptions.FilterException;
import aperf.api.filter.IFilter;
import aperf.exceptions.APerfCommandException;
import aperf.proxy.LocalizationProxy;
import aperf.subsystem.module.ModuleSubsystem;
import myessentials.Localization;
import myessentials.command.CommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands {

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

    protected static Localization getLocal() {
        return LocalizationProxy.getLocalization();
    }
}
