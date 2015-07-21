package aperf.modules.spawnlimiter;

import aperf.APerf;
import aperf.api.SpawnLimitRegistrar;
import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.SpawnLimit;
import cpw.mods.fml.common.discovery.ASMDataTable;

import java.util.Set;

public class SpawnLimitLoader {
    private SpawnLimitLoader() {
    }

    public static void load(ASMDataTable asmDataTable) {
        Set<ASMDataTable.ASMData> data = asmDataTable.getAll(SpawnLimit.class.getName());

        for (ASMDataTable.ASMData asm : data) {
            registerSpawnLimit(asm);
        }
    }

    private static void registerSpawnLimit(ASMDataTable.ASMData data) {
        Class<?> c;
        String className = data.getClassName();

        try {
            c = Class.forName(className);
        } catch(ClassNotFoundException e) {
            APerf.LOG.info("Can't find the Spawn Limit!", e);
            return;
        }

        // Is it actually an ISpawnLimit
        if (!ISpawnLimit.class.isAssignableFrom(c)) {
            APerf.LOG.warn(c.getName() + " is not an ISpawnLimit.");
            return;
        }

        // Check for a no-args ctor
        try {
            c.getConstructor();
        } catch(NoSuchMethodException e) {
            APerf.LOG.warn("Missing Constructor. Must have a no-arguments constructor to be a SpawnLimit!", e);
            return;
        }

        SpawnLimit annot = c.getAnnotation(SpawnLimit.class);
        if (annot == null) {
            APerf.LOG.warn(c.getName() + " does NOT have a SpawnLimit annotation.");
            return;
        }

        @SuppressWarnings("unchecked")
        Class<ISpawnLimit> limit = (Class<ISpawnLimit>) c;

        SpawnLimitRegistrar.INSTANCE.addSpawnLimit(limit, annot);
        APerf.LOG.info("Registering SpawnLimit: " + annot.name());
    }
}
