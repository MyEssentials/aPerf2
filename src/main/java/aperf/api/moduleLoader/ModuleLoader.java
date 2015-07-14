package aperf.api.moduleLoader;

import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.EventBus;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Annotation based module loader.
 * Searches for the specific annotation on classes and wraps an instance in ModuleContainer
 *
 * Loosely based on ForgeEssentials ModuleLauncher, but made more generic! :D
 */
public abstract class ModuleLoader<ModuleAnnotation> {
    private final EventBus BUS;
    private final String annotationName;
    private final Map<String, ModuleContainer<ModuleAnnotation>> modules;
    private final Logger logger;

    public ModuleLoader(String annotationName, Logger logger) {
        BUS = new EventBus();
        modules = new HashMap<String, ModuleContainer<ModuleAnnotation>>();
        this.annotationName = annotationName;
        this.logger = logger;
    }

    public void preInit(FMLPreInitializationEvent ev) {
        Set<ASMDataTable.ASMData> data = ev.getAsmData().getAll(annotationName);

        ModuleContainer<ModuleAnnotation> container;
        for (ASMDataTable.ASMData asm : data) {
            container = new ModuleContainer<ModuleAnnotation>(this, asm);
            logger.info("Found Module! " + container.getName());

            if (container.isEnabled() && !modules.containsKey(container.getName())) {
                logger.info("Registered Module! " + container.getName());
                registerWithBus(container);
                modules.put(container.getName(), container);
            }
        }

        BUS.post(new ModuleEvent.ModulePreInitEvent(ev));
    }

    public void init(FMLInitializationEvent ev) {
        BUS.post(new ModuleEvent.ModuleInitEvent(ev));
    }

    public void postInit(FMLPostInitializationEvent ev) {
        BUS.post(new ModuleEvent.ModulePostInitEvent(ev));
    }

    public void serverAboutToStart(FMLServerAboutToStartEvent ev) {
        BUS.post(new ModuleEvent.ModuleServerPreInitEvent(ev));
    }

    public void serverStarting(FMLServerStartingEvent ev) {
        BUS.post(new ModuleEvent.ModuleServerInitEvent(ev));
    }

    public void serverStarted(FMLServerStartedEvent ev) {
        BUS.post(new ModuleEvent.ModuleServerPostInitEvent(ev));
    }

    public void serverStopping(FMLServerStoppingEvent ev) {
        BUS.post(new ModuleEvent.ModuleServerStopEvent(ev));
    }

    public void serverStopped(FMLServerStoppedEvent ev) {
        BUS.post(new ModuleEvent.ModuleServerStoppedEvent(ev));
    }

    private void registerWithBus(ModuleContainer<ModuleAnnotation> container) {
        BUS.register(container.getModule());
    }

    protected abstract ModuleAnnotation getAnnotation(Class<?> c);

    protected abstract String getModuleName(ModuleContainer<ModuleAnnotation> container);

    protected abstract boolean shouldModuleLoad(ModuleContainer<ModuleAnnotation> container);
}
