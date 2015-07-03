package aperf;

import aperf.modules.loader.APModuleLoader;
import aperf.proxy.sided.IProxy;
import aperf.subsystem.FilterSubsystem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import mytown.core.config.ConfigProcessor;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = "aPerf", name = "aPerf", version = "2.0", dependencies = "required-after:Forge;required-after:MyEssentials-Core", acceptableRemoteVersions = "*")
public class APerf {
    @Mod.Instance
    public static APerf INSTANCE;
    public static Logger LOG;
    private static APModuleLoader MODULELOADER;

    private Configuration config;

    @SidedProxy(clientSide = "aperf.proxy.sided.ClientProxy", serverSide = "aperf.proxy.sided.ServerProxy")
    private static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent ev) {
        // Setup Loggers
        LOG = ev.getModLog();

        // Read Config
        Constants.CONFIG_FOLDER = ev.getModConfigurationDirectory().getPath() + "/aPerf/";
        config = new Configuration(new File(Constants.CONFIG_FOLDER, "aPerf.cfg"));
        ConfigProcessor.load(config, Config.class);

        // Initialize Subsystems
        FilterSubsystem.load(ev.getAsmData());

        // Initialize Modules
        MODULELOADER = new APModuleLoader();
        MODULELOADER.preInit(ev);

        // Initialize Proxy
        proxy.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        MODULELOADER.init(ev);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent ev) {
        MODULELOADER.postInit(ev);
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent ev) {
        MODULELOADER.serverAboutToStart(ev);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent ev) {
        MODULELOADER.serverStarting(ev);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent ev) {
        MODULELOADER.serverStarted(ev);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent ev) {
        MODULELOADER.serverStopping(ev);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent ev) {
        MODULELOADER.serverStopped(ev);
    }
}
