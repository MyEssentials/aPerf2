package aperf.api.filter;

import aperf.api.FilterRegistrar;
import aperf.api.exceptions.FilterException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Makes multiple filters act as one. All or nothing.
 */
@Filter(name = "Multi", desc = "Makes multiple filters act as one. All or nothing.", valueDesc = "Array of more filters", isGrouper = false)
public class MultiFilter implements IFilter {
    private List<IFilter> filters = new ArrayList<IFilter>();

    @Override
    public boolean hits(Object o) {
        for (IFilter filter : filters) {
            if (!filter.hits(o))
                return false;
        }

        return true;
    }

    @Override
    public String group(Object o) {
        return null; // This does NOT support grouping!
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                if (!element.isJsonObject()) continue;
                try {
                    filters.add(JsonFilterMarshaler.unmarshal(element.getAsJsonObject()));
                } catch (FilterException.FilterCreationException e) {
                    throw new FilterException.FilterLoadException(this, "Sub-filter failed to create", e);
                } catch (FilterException.FilterNotFoundException e) {
                    throw new FilterException.FilterLoadException(this, "Sub-filter failed to load", e);
                }
            }
        }
    }

    @Override
    public void load(String str) throws FilterException.FilterLoadException {
        String[] filterParts = str.split(";");
        for (String filter : filterParts) {
            String filterName = filter.substring(0, filter.indexOf(':'));
            String filterConfig = filter.substring(filter.indexOf(':') + 1);
            try {
                filters.add(FilterRegistrar.INSTANCE.loadFilter(filterName, filterConfig));
            } catch (FilterException.FilterCreationException e) {
                throw new FilterException.FilterLoadException(this, "Sub-filter failed to create", e);
            } catch (FilterException.FilterNotFoundException e) {
                throw new FilterException.FilterLoadException(this, "Sub-filter failed to load", e);
            }
        }
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        JsonArray ret = new JsonArray();
        for (IFilter filter : filters) {
            JsonObject subFilter;
            try {
                subFilter = JsonFilterMarshaler.marshal(filter);
            } catch (FilterException.FilterSaveException e) {
                throw new FilterException.FilterSaveException(this, "Sub-filter failed to save", e);
            }
            if (subFilter == null) continue;
            ret.add(subFilter);
        }
        return ret;
    }

    @Override
    public String toString() {
        return String.format("Multi(%s)", filters);
    }

    public void addFilter(IFilter filter) {
        filters.add(filter);
    }

    public void removeFilter(IFilter filter) {
        if (!filters.remove(filter)) {
            for (IFilter subF : filters) {
                if (subF instanceof MultiFilter) {
                    ((MultiFilter) subF).removeFilter(filter);
                }
            }
        }
    }
}
