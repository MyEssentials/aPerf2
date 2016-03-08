package aperf.modules.spawnlimiter;

import aperf.APerf;
import aperf.Constants;
import aperf.api.moduleLoader.ModuleEvent;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.modules.entity.Config;
import aperf.modules.spawnlimiter.cmd.SpawnLimitCommands;
import aperf.modules.spawnlimiter.cmd.creation.SpawnLimitCreationCommands;
import aperf.modules.spawnlimiter.cmd.creation.SpawnLimitFilterCommans;
import aperf.modules.spawnlimiter.config.LimitsConfig;
import aperf.subsystem.module.APModule;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mypermissions.command.api.CommandManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

@APModule(name = "SpawnLimiter")
public class SpawnLimiterModule {
    EventListeners events;

    @SubscribeEvent
    public void preInit(ModuleEvent.ModulePreInitEvent ev) {
        SpawnLimitLoader.load(ev.getFMLEvent().getAsmData());
        Config.instance.init(Constants.CONFIG_FOLDER + "spawnlimiter.cfg", "aPerf2");
        LimitsConfig.load();
        // Setup Event Listeners
        events = new EventListeners();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }

    @SubscribeEvent
    public void serverStarting(ModuleEvent.ModuleServerInitEvent ev) {
        CommandManager.registerCommands(SpawnLimitCommands.class, "aperf.cmd", null, null);
        CommandManager.registerCommands(SpawnLimitCreationCommands.class, "aperf.cmd.module.entity.spawn", null, null);
        CommandManager.registerCommands(SpawnLimitFilterCommans.class, "aperf.cmd.module.entity.spawn", null, null);
    }

    @SubscribeEvent
    public void serverStopped(ModuleEvent.ModuleServerStoppedEvent ev) {
        LimitsConfig.save();
    }

    /**
     * Returns if the given entity can spawn in the world
     * @param entity The entity to check
     * @param world The world to check
     * @return If the entity can spawn
     */
    public static boolean findLimitAndCheck(EntityLivingBase entity, World world) {
        if (entity instanceof EntityPlayer)
            return true;

        for (ISpawnLimit limit : LimitsConfig.Limits) {
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
