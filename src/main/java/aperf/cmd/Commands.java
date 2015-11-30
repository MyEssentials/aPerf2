package aperf.cmd;

import aperf.APerf;
import aperf.api.FilterRegistrar;
import aperf.api.exceptions.FilterException;
import aperf.api.filter.IFilter;
import aperf.exceptions.APerfCommandException;
import aperf.subsystem.module.ModuleSubsystem;
import myessentials.Localization;
import mypermissions.api.command.CommandCompletion;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    public static void sendMessageBackToSender(ICommandSender sender, String message) {
        sender.addChatMessage(new ChatComponentText(message));
    }

    public static void populateCompletionMap() {
        List<String> populator = new ArrayList<String>();
        populator.addAll(ModuleSubsystem.Instance().getModuleNames());
        CommandCompletion.addCompletions("aperfModuleCompletion", populator);
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
            throw new APerfCommandException("aperf.filter.notfound", e, e.getFilterName());
        } catch (FilterException.FilterCreationException e) {
            throw new APerfCommandException("aperf.filter.creation.failed", e, e.getFilterName());
        } catch (FilterException.FilterLoadException e) {
            throw new APerfCommandException("aperf.filter.load.failed", e, e.getFilterName());
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
            throw new APerfCommandException("aperf.group.notfound", e, e.getFilterName());
        } catch (FilterException.FilterCreationException e) {
            throw new APerfCommandException("aperf.group.creation.failed", e, e.getFilterName());
        }
    }

    protected static Localization getLocal() {
        return APerf.LOCAL;
    }
}
