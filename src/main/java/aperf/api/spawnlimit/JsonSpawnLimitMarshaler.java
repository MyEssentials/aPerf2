package aperf.api.spawnlimit;

import aperf.APerf;
import aperf.api.SpawnLimitRegistrar;
import aperf.api.exceptions.FilterException;
import aperf.api.filter.JsonFilterMarshaler;
import com.google.gson.JsonObject;

/**
 */
public class JsonSpawnLimitMarshaler {
    public static ISpawnLimit unmarshal(JsonObject jsonObject) {
        APerf.LOG.info("Un-marshaling ISpawnLimit");
        if (jsonObject == null) return null; // TODO Throw exception?
        if (!jsonObject.has("type")) return null; // TODO Throw exception?
        String spawnLimitType = jsonObject.getAsJsonPrimitive("type").getAsString();
        APerf.LOG.info("Un-marshaling", spawnLimitType);
        ISpawnLimit limit = SpawnLimitRegistrar.INSTANCE.loadSpawnLimit(spawnLimitType, jsonObject.get("value"));
        if (jsonObject.has("filter")) {
            try {
                limit.setFilter(JsonFilterMarshaler.unmarshal(jsonObject.getAsJsonObject("filter")));
            } catch (FilterException.FilterNotFoundException e) {
                e.printStackTrace();
            } catch (FilterException.FilterLoadException e) {
                e.printStackTrace();
            } catch (FilterException.FilterCreationException e) {
                e.printStackTrace();
            }
        }
        return limit;
    }

    public static JsonObject marshal(ISpawnLimit spawnLimit) {
        if (spawnLimit == null) return null; // TODO Throw exception?
        SpawnLimit annot = spawnLimit.getClass().getAnnotation(SpawnLimit.class);
        if (annot == null) return null; // TODO Throw exception?
        JsonObject ret = new JsonObject();
        ret.addProperty("type", annot.name());
        if (spawnLimit.getFilter() != null) {
            try {
                ret.add("filter", spawnLimit.getFilter().save());
            } catch (FilterException.FilterSaveException e) {
                e.printStackTrace();
            }
        }
        ret.add("value", spawnLimit.save());
        return ret;
    }
}
