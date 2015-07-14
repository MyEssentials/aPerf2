package aperf.api.moduleLoader;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Events to be caught by modules.
 * Most are just re-broadcasts of FML lifetime events.
 *
 * @param <E> The FML Event to build off of
 */
public class ModuleEvent<E extends FMLStateEvent> extends Event {
    E event;

    public E getFMLEvent() {
        return event;
    }

    public static class ModulePreInitEvent extends ModuleEvent<FMLPreInitializationEvent> {
        public ModulePreInitEvent(FMLPreInitializationEvent ev) {
            this.event = ev;
        }
    }

    public static class ModuleInitEvent extends ModuleEvent<FMLInitializationEvent> {
        public ModuleInitEvent(FMLInitializationEvent ev) {
            this.event = ev;
        }
    }

    public static class ModulePostInitEvent extends ModuleEvent<FMLPostInitializationEvent> {
        public ModulePostInitEvent(FMLPostInitializationEvent ev) {
            this.event = ev;
        }

        public Object buildSoftDependProxy(String modId, String className) {
            return this.event.buildSoftDependProxy(modId, className);
        }
    }

    /* Server Events */

    public static class ModuleServerPreInitEvent extends ModuleEvent<FMLServerAboutToStartEvent> {
        public ModuleServerPreInitEvent(FMLServerAboutToStartEvent ev) {
            this.event = ev;
        }
    }

    public static class ModuleServerInitEvent extends ModuleEvent<FMLServerStartingEvent> {
        public ModuleServerInitEvent(FMLServerStartingEvent ev) {
            this.event = ev;
        }
    }

    public static class ModuleServerPostInitEvent extends ModuleEvent<FMLServerStartedEvent> {
        public ModuleServerPostInitEvent(FMLServerStartedEvent ev) {
            this.event = ev;
        }
    }

    public static class ModuleServerStopEvent extends ModuleEvent<FMLServerStoppingEvent> {
        public ModuleServerStopEvent(FMLServerStoppingEvent ev) {
            this.event = ev;
        }
    }

    public static class ModuleServerStoppedEvent extends ModuleEvent<FMLServerStoppedEvent> {
        public ModuleServerStoppedEvent(FMLServerStoppedEvent ev) {
            this.event = ev;
        }
    }
}
