package aperf.filters;

import aperf.api.exceptions.FilterException;
import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;
import com.google.gson.JsonElement;

/**
 * Hits anything and everything
 */
@Filter(name = "All", desc = "Hits everything", valueDesc = "No Value")
public class AllFilter implements IFilter {
    public AllFilter() {
    }

    @Override
    public boolean hits(Object o) {
        return true;
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        return null;
    }
}
