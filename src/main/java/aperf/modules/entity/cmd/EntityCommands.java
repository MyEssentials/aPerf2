package aperf.modules.entity.cmd;

import aperf.api.filter.IFilter;
import aperf.api.grouper.Grouper;
import aperf.cmd.Commands;
import aperf.exceptions.APerfWrongUsageException;
import myessentials.command.CommandManager;
import myessentials.command.CommandNode;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;

import java.util.*;

public class EntityCommands extends Commands {
    @CommandNode(
            name = "entity",
            permission = "aperf.cmd.module.entity",
            parentName = "aperf.cmd",
            alias = {"e"})
    public static void entityCommand(ICommandSender sender, List<String> args) {
        CommandManager.callSubFunctions(sender, args, "aperf.cmd.module.entity", getLocal());
    }

    @CommandNode(
            name = "list",
            permission = "aperf.cmd.module.entity.list",
            parentName = "aperf.cmd.module.entity",
            alias = {"l"})
    public static void listEntitiesCommand(ICommandSender sender, List<String> args) {
        // args: [group] [filter] [limit]

        // Get Group
        String groupName;
        IFilter grouperFilter = null;
        if (args.size() >= 1 && args.get(0) != null) {
            groupName = args.get(0);
            grouperFilter = loadGroup(groupName);
        }

        // Get Filter
        String filterArg;
        IFilter filter = null;
        if (args.size() >= 2 && args.get(1) != null) {
            filterArg = args.get(1);
            filter = loadFilter("Multi", filterArg);
        }

        // Get Limit
        int limitStart = 0;
        int limitCount = 0;
        if (args.size() >= 3 && args.get(2) != null) {
            String limitArg = args.get(2);
            String[] limitArgParts = limitArg.split("-");
            limitStart = Integer.parseInt(limitArgParts[0]);

            if (limitArgParts.length > 1) {
                limitCount = Integer.parseInt(limitArgParts[1]);
            }
        }

        Grouper<Entity> grouper = new Grouper<Entity>(filter, grouperFilter);

        sendMessageBackToSender(sender, String.format("%s----------------------------------", EnumChatFormatting.GRAY));
        for (WorldServer world : MinecraftServer.getServer().worldServers) {
            List<?> list = world.loadedEntityList;
            if (list.size() <= 0) continue;
            int total = 0;
            List<Map.Entry<String, Integer>> groups = grouper.group((List<Entity>) list);
            for (Map.Entry<String, Integer> e : groups) {
                total += e.getValue();
            }

            if (total > 0) {
                sendMessageBackToSender(sender, String.format("%s%s [%d], %s %s", EnumChatFormatting.GREEN, world.provider.getDimensionName(), world.provider.dimensionId, total, total == 1 ? "entity" : "entities"));
                sendCountedList(sender, "   ", groups, limitStart, limitCount);
            }
        }
        sendMessageBackToSender(sender, String.format("%s----------------------------------", EnumChatFormatting.GRAY));
    }

    @CommandNode(
            name = "listaround",
            permission = "aperf.cmd.module.entity.listaround",
            parentName = "aperf.cmd.module.entity",
            alias = {"la"})
    public static void listAroundCommand(ICommandSender sender, List<String> args) {
        // args: <radius> [group] [filter] [limit]
        if (args.size() < 1) throw new APerfWrongUsageException("/aperf entity listaround <radius> [group] [filter] [limit]");
        if (!(sender instanceof EntityPlayer)) return; // TODO Throw an exception
        EntityPlayer p = (EntityPlayer) sender;
        int radius = Integer.parseInt(args.get(0));

        String filter = args.get(2);
        filter = filter != null && filter.length() > 0 ? filter + "," : "";

        filter = filter + String.format("dim:%d,pos:%d.%d.%d/%d.%d.%d", p.dimension, (int) p.posX - radius, (int) p.posY - radius, (int) p.posZ - radius, (int) p.posX + radius, (int) p.posY + radius, (int) p.posZ + radius);
        args.set(2, filter);

        listEntitiesCommand(sender, args.subList(1, args.size()));
    }

    @CommandNode(
            name = "listnearhere",
            permission = "aperf.cmd.module.entity.listnearhere",
            parentName = "aperf.cmd.module.entity",
            alias = {"lnh"})
    public static void listNearHereCommand(ICommandSender sender, List<String> args) {
        // args: <radius> [group] [filter] [limit]
        if (args.size() < 1) throw new APerfWrongUsageException("/aperf entity listnearhere <radius> [group] [filter] [limit]");
        if (!(sender instanceof EntityPlayer)) return; // TODO Throw an exception
        EntityPlayer p = (EntityPlayer) sender;
        int radius = Integer.parseInt(args.get(0));

        String filter = args.get(2);
        filter = filter != null && filter.length() > 0 ? filter + "," : "";

        filter = filter + String.format("dim:%d,where:%d.%d/%d.%d", p.dimension, p.chunkCoordX - radius, p.chunkCoordZ - radius, p.chunkCoordX + radius, p.chunkCoordZ + radius);
        args.set(2, filter);

        listEntitiesCommand(sender, args.subList(1, args.size()));
    }

    @CommandNode(
            name = "listhere",
            permission = "aperf.cmd.module.entity.listhere",
            parentName = "aperf.cmd.module.entity",
            alias = {"lh"})
    public static void listHereCommand(ICommandSender sender, List<String> args) {
        // args: [group] [filter] [limit]
        List<String> newArgs = new ArrayList<String>();
        newArgs.set(0, "0");
        if (args != null) {
            newArgs.addAll(args);
        }
        listNearHereCommand(sender, args);
    }

    protected static void sendCountedList(ICommandSender sender, String prefix, List<Map.Entry<String, Integer>> counts, Integer from, Integer count) {
        if (counts == null) return;
        if (from == null || from < 0 || from >= counts.size()) from = 0;
        if (count == null || count.equals(from) || from+count >= counts.size()) count = counts.size()-from;
        List<Map.Entry<String, Integer>> subList = counts.subList(from, from+count);
        String maxCntLen = null;
        for (Map.Entry<String, Integer> entry : subList) {
            if (maxCntLen == null) {
                maxCntLen = String.valueOf(entry.getValue().toString().length());
            }

            sendMessageBackToSender(sender, String.format("%s%s%" + maxCntLen + "d %s| %s%s", prefix, EnumChatFormatting.RED, entry.getValue(), EnumChatFormatting.GREEN, EnumChatFormatting.YELLOW, entry.getKey()));
        }
    }
}
