package aperf.filters;

import aperf.api.exceptions.FilterException;
import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * Checks the Java hashcode
 */
@Filter(name = "Hash", desc = "Checks the Java hashcode", valueDesc = "Hash. Ex: 4d4a2b4d")
public class HashFilter implements IFilter {
    private int objHashCode;

    @Override
    public boolean hits(Object o) {
        return o != null && System.identityHashCode(o) == objHashCode;
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        if (json.isJsonPrimitive()) {
            objHashCode = json.getAsInt();
        }
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        return new JsonPrimitive(objHashCode);
    }
}
