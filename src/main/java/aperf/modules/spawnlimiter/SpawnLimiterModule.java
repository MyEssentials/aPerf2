package aperf.modules.spawnlimiter;

import aperf.api.moduleLoader.ModuleEvent;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.modules.spawnlimiter.cmd.SpawnLimitCommands;
import aperf.modules.spawnlimiter.cmd.creation.SpawnLimitCreationCommands;
import aperf.modules.spawnlimiter.cmd.creation.SpawnLimitFilterCommans;
import aperf.subsystem.module.APModule;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import myessentials.command.CommandManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

@APModule(name = "SpawnLimiter")
public class SpawnLimiterModule {
    EventListeners events;

    @SubscribeEvent
    public void preInit(ModuleEvent.ModulePreInitEvent ev) {
        SpawnLimitLoader.load(ev.getFMLEvent().getAsmData());
        Config.load();
        // Setup Event Listeners
        events = new EventListeners();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }

    @SubscribeEvent
    public void serverStarting(ModuleEvent.ModuleServerInitEvent ev) {
        CommandManager.registerCommands(SpawnLimitCommands.class);
        CommandManager.registerCommands(SpawnLimitCreationCommands.class);
        CommandManager.registerCommands(SpawnLimitFilterCommans.class);
    }

    @SubscribeEvent
    public void serverStopped(ModuleEvent.ModuleServerStoppedEvent ev) {
        Config.save();
    }

    /**
     * Returns if the given entity can spawn in the world
     * @param entity The entity to check
     * @param world The world to check
     * @return If the entity can spawn
     */
    public static boolean findLimitAndCheck(EntityLivingBase entity, World world) {
        for (ISpawnLimit limit : Config.Limits) {
            if (!limit.isEnabled())
                continue;

            if (!limit.hits(entity))
                continue;

            if (!limit.canSpawn(entity, world))
                return false;
        }

        return true;
    }
}
