package aperf;

import aperf.cmd.APerfCommand;
import aperf.cmd.Commands;
import aperf.cmd.ModuleCommands;
import aperf.proxy.LocalizationProxy;
import aperf.proxy.sided.IProxy;
import aperf.subsystem.FilterSubsystem;
import aperf.subsystem.module.ModuleSubsystem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import myessentials.new_config.ConfigProcessor;
import mypermissions.command.CommandManager;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = "aPerf2", name = "aPerf2", version = "2.0", dependencies = "required-after:Forge;required-after:MyEssentials-Core", acceptableRemoteVersions = "*")
public class APerf {
    @Mod.Instance
    public static APerf INSTANCE;
    public static Logger LOG;

    @SidedProxy(clientSide = "aperf.proxy.sided.ClientProxy", serverSide = "aperf.proxy.sided.ServerProxy")
    private static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        // Setup Loggers
        LOG = ev.getModLog();

        // Read Config
        Constants.CONFIG_FOLDER = ev.getModConfigurationDirectory().getPath() + "/aPerf/";
        Configuration forgeConfig = new Configuration(new File(Constants.CONFIG_FOLDER, "aPerf.cfg"));
        ConfigProcessor.load(Config.class, forgeConfig);

        LocalizationProxy.load();

        // Initialize Subsystems
        FilterSubsystem.load(ev.getAsmData());
        ModuleSubsystem.Instance().preInit(ev);

        // Initialize Proxy
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        ModuleSubsystem.Instance().init(ev);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {
        ModuleSubsystem.Instance().postInit(ev);
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent ev) {
        ModuleSubsystem.Instance().serverAboutToStart(ev);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent ev) {
        registerCommands();

        ModuleSubsystem.Instance().serverStarting(ev);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent ev) {
        ModuleSubsystem.Instance().serverStarted(ev);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent ev) {
        ModuleSubsystem.Instance().serverStopping(ev);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent ev) {
        ModuleSubsystem.Instance().serverStopped(ev);
    }

    private void registerCommands() {
        CommandManager.registerCommands(APerfCommand.class, null, LocalizationProxy.getLocalization(), null);
        CommandManager.registerCommands(ModuleCommands.class, "aperf.cmd", LocalizationProxy.getLocalization(), null);

        Commands.populateCompletionMap();
    }
}
