package aperf.subsystem;

import aperf.APerf;
import aperf.api.FilterRegistrar;
import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;
import cpw.mods.fml.common.discovery.ASMDataTable;

import java.util.Set;

public class FilterSubsystem {
    private FilterSubsystem() {
    }

    public static void load(ASMDataTable asmDataTable) {
        Set<ASMDataTable.ASMData> data = asmDataTable.getAll(Filter.class.getName());

        for (ASMDataTable.ASMData asm : data) {
            registerFilter(asm);
        }
    }

    private static void registerFilter(ASMDataTable.ASMData data) {
        Class<?> c;
        String className = data.getClassName();

        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            APerf.LOG.info("Can't find the Filter!", e);
            return;
        }

        // Is it actually an IFilter?
        if (!IFilter.class.isAssignableFrom(c)) {
            APerf.LOG.warn(c.getName() + " is not an IFilter.");
            return;
        }

        // Check for a no-args ctor
        try {
            c.getConstructor();
        } catch (NoSuchMethodException e) {
            APerf.LOG.warn("Missing Constructor. Must have a no-arguments constructor to be a Filter!", e);
            return;
        }

        Filter annot = c.getAnnotation(Filter.class);
        if (annot == null) {
            APerf.LOG.warn(c.getName() + " does NOT have a Filter annotation.");
            return;
        }

        Class<IFilter> filter = (Class<IFilter>) c;

        FilterRegistrar.INSTANCE.addFilter(filter, annot);
        APerf.LOG.info("Registering Filter: " + annot.name());
    }
}
