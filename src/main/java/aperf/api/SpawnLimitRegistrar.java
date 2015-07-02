package aperf.api;

import aperf.api.spawnlimit.ISpawnLimit;
import aperf.api.spawnlimit.SpawnLimit;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class SpawnLimitRegistrar {
    public static final SpawnLimitRegistrar INSTANCE = new SpawnLimitRegistrar();

    private Map<String, SpawnLimitWrapper> limits;

    private SpawnLimitRegistrar() {
        limits = new HashMap<String, SpawnLimitWrapper>();
    }

    /**
     * Registers a new {@link ISpawnLimit}
     * Do NOT use this directly! Used internally ONLY!
     * @param limit The {@link ISpawnLimit} class to register
     * @param annot The {@link SpawnLimit} annotation to register the limit with
     */
    public void addSpawnLimit(Class<ISpawnLimit> limit, SpawnLimit annot) {
        String name = annot.name();
        limits.put(name, new SpawnLimitWrapper(limit, annot));
    }

    /**
     * Gets an {@link ISpawnLimit} class from the given name
     * @param name Name of the {@link ISpawnLimit} to get
     * @return The {@link ISpawnLimit} class
     */
    public Class<ISpawnLimit> getSpawnLimitClass(String name) {
        if (!limits.containsKey(name)) {
        }

        return limits.get(name).getLimitClass();
    }

    /**
     * Creates an {@link ISpawnLimit} instance from the given name
     * @param name Name of the {@link ISpawnLimit} to create
     * @return The newly created {@link ISpawnLimit}
     */
    public ISpawnLimit createSpawnLimit(String name) {
        Class<ISpawnLimit> limitClass = getSpawnLimitClass(name);

        try {
            return limitClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Creates and loads an {@link ISpawnLimit} instance from the given name
     * @param name Name of the {@link ISpawnLimit} to create
     * @param jsonConf JSON config
     * @return The newly created and loaded {@link ISpawnLimit}
     */
    public ISpawnLimit loadSpawnLimit(String name, JsonElement jsonConf) {
        ISpawnLimit limit = createSpawnLimit(name);
        limit.load(jsonConf);
        return limit;
    }

    /**
     * Creates and loads an {@link ISpawnLimit} instance from the given name
     * @param name Name of the {@link ISpawnLimit} to create
     * @param stringConf String config
     * @return The newly created and loaded {@link ISpawnLimit}
     */
    public ISpawnLimit loadSpawnLimit(String name, String stringConf) {
        ISpawnLimit limit = createSpawnLimit(name);
        limit.load(stringConf);
        return limit;
    }

    /**
     * Gets the description of the {@link ISpawnLimit}
     * @param name The name
     * @return The description of the {@link ISpawnLimit}
     */
    public String getDesc(String name) {
        return limits.get(name).getDesc();
    }

    /**
     * Returns a List of the names of the registered {@link ISpawnLimit}s
     * @return The List of names
     */
    public List<String> getSpawnLimitNames() {
        List<String> ret = new ArrayList<String>();
        for (SpawnLimitWrapper wrapper : limits.values()) {
            ret.add(wrapper.getName());
        }
        return ret;
    }

    /**
     * Internal wrapper around an {@link ISpawnLimit} and its {@link SpawnLimit} annotation.
     */
    private class SpawnLimitWrapper {
        Class<ISpawnLimit> limit;
        private SpawnLimit annot;

        public SpawnLimitWrapper(Class<ISpawnLimit> limit, SpawnLimit annot) {
            this.limit = limit;
            this.annot = annot;
        }

        /**
         * Returns the name of the {@link ISpawnLimit}
         * @return The name
         */
        public String getName() {
            return annot.name();
        }

        /**
         * Returns the description of the {@link ISpawnLimit}
         * @return The description
         */
        public String getDesc() {
            return annot.desc();
        }

        /**
         * Returns the {@link ISpawnLimit} Class
         * @return
         */
        public Class<ISpawnLimit> getLimitClass() {
            return limit;
        }
    }
}
