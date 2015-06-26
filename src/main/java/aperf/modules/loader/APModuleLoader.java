package aperf.modules.loader;

import aperf.moduleLoader.ModuleContainer;
import aperf.moduleLoader.ModuleLoader;

/**
 * aPerf specific ModuleLoader.
 * Uses APModule to mark a class as a module
 */
public class APModuleLoader extends ModuleLoader<APModule> {
    public APModuleLoader() {
        super(APModule.class.getName());
    }

    protected APModule getAnnotation(Class<?> c) {
        return c.getAnnotation(APModule.class);
    }

    protected String getModuleName(ModuleContainer<APModule> container) {
        return container.getAnnotation().name();
    }

    protected boolean shouldModuleLoad(ModuleContainer<APModule> container) {
        if (container.getAnnotation().canDisable()) {
            // TODO Disable from config
        }

        return true;
    }
}
