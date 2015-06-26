package aperf.moduleLoader;

import cpw.mods.fml.common.discovery.ASMDataTable;

/**
 * A wrapper around a module instance.
 * Handles basic info (name, enabled, etc)
 *
 * @param <ModuleAnnotation>
 */
public class ModuleContainer<ModuleAnnotation> {
    private ModuleLoader<ModuleAnnotation> moduleLoader;
    private Object module;
    private String name;
    private boolean enabled = false;
    private ModuleAnnotation annotation = null;

    public ModuleContainer(ModuleLoader<ModuleAnnotation> moduleLoader, ASMDataTable.ASMData data) {
        this.moduleLoader = moduleLoader;

        Class<?> c = null;
        String className = data.getClassName();

        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // TODO Logging
            return;
        }

        annotation = moduleLoader.getAnnotation(c);
        if (annotation == null)
            return;

        try {
            module = c.newInstance();
        } catch (Exception e) {
            e.printStackTrace(); // TODO Logging
            return;
        }

        name = moduleLoader.getModuleName(this);
        enabled = moduleLoader.shouldModuleLoad(this);
    }

    public String getName() {
        return this.name;
    }

    public Object getModule() {
        return this.module;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public ModuleAnnotation getAnnotation() {
        return annotation;
    }

    private boolean runPreConditions() {
        // TODO Implement preconditions
        return  true;
    }
}
