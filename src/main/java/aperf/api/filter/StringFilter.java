package aperf.api.filter;

import aperf.api.exceptions.FilterException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

/**
 * {@link IFilter} to load String value from the given Json
 */
public abstract class StringFilter implements IFilter {
    protected String value;

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        if (json.isJsonPrimitive()) {
            value = json.getAsString();
        } else {
            throw new FilterException.FilterLoadException(this, "Invalid configuration! Should be a String!");
        }
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        return new JsonPrimitive(value);
    }

    @Override
    public void load(String str) throws FilterException.FilterLoadException {
        this.value = str;
    }
}
