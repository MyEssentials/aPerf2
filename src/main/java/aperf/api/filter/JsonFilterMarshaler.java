package aperf.api.filter;

import aperf.api.FilterRegistrar;
import aperf.api.exceptions.FilterException;
import com.google.gson.JsonObject;

/**
 * Helper functions to marshal/un-marshal an IFilter
 */
public class JsonFilterMarshaler {
    /**
     * Turns the given JsonObject into an IFilter
     * @param jsonObject JsonObject to un-marshal
     * @return Newly created and loaded IFilter
     * @throws FilterException.FilterNotFoundException
     * @throws FilterException.FilterLoadException
     * @throws FilterException.FilterCreationException
     */
    public static IFilter unmarshal(JsonObject jsonObject) throws FilterException.FilterNotFoundException, FilterException.FilterLoadException, FilterException.FilterCreationException {
        if (jsonObject == null) return null; // TODO Throw exception?
        if (!jsonObject.has("type")) return null; // TODO Throw exception?
        String filterType = jsonObject.getAsJsonPrimitive("type").getAsString();
        return FilterRegistrar.INSTANCE.loadFilter(filterType, jsonObject.get("value"));
    }

    /**
     * Turns the given IFilter into a JsonObject
     * @param filter The filter to marshal
     * @return The marshaled JsonObject
     * @throws FilterException.FilterSaveException
     */
    public static JsonObject marshal(IFilter filter) throws FilterException.FilterSaveException {
        if (filter == null) return null; // TODO Throw exception?
        Filter annot = filter.getClass().getAnnotation(Filter.class);
        if (annot == null) return null; // TODO Throw exception?
        JsonObject ret = new JsonObject();
        ret.addProperty("type", annot.name());
        ret.add("value", filter.save());
        return ret;
    }
}
