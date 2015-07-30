package aperf.subsystem.module;

import aperf.APerf;
import aperf.Config;
import aperf.api.moduleLoader.ModuleContainer;
import aperf.api.moduleLoader.ModuleLoader;

public class ModuleSubsystem extends ModuleLoader<APModule> {
    private static ModuleSubsystem INSTANCE;

    public static ModuleSubsystem Instance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleSubsystem();
        }

        return INSTANCE;
    }

    private ModuleSubsystem() {
        super(APModule.class.getName(), APerf.LOG);
    }

    protected APModule getAnnotation(Class<?> c) {
        return c.getAnnotation(APModule.class);
    }

    protected String getModuleName(ModuleContainer<APModule> container) {
        return container.getAnnotation().name();
    }

    protected boolean shouldModuleLoad(ModuleContainer<APModule> container) {
        if (container.getAnnotation().canDisable()) {
            for (String loadedModule : Config.General.LoadedModules) {
                if (loadedModule.equals("*") || loadedModule.toLowerCase().equals(container.getName().toLowerCase())) {
                    return true;
                }
            }

            return false;
        }

        return true;
    }
}
