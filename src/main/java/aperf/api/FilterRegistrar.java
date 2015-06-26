package aperf.api;

import aperf.api.exceptions.FilterException;
import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class FilterRegistrar {
    public static final FilterRegistrar INSTANCE = new FilterRegistrar();

    private Map<String, FilterWrapper> filters;

    private FilterRegistrar() {
        filters = new HashMap<String, FilterWrapper>();
    }

    /**
     * Registers a new IFilter.
     * Do NOT use this directly! Used internally ONLY!
     * @param filterClass
     * @param annot
     */
    public void addFilter(Class<IFilter> filterClass, Filter annot) {
        String name = annot.name();
        filters.put(name, new FilterWrapper(filterClass, annot));
    }

    /**
     * Gets an IFilter class from the given name
     * @param name Name of the IFilter to create
     * @return The IFilter Class
     * @throws FilterException.FilterNotFoundException
     */
    public Class<IFilter> getFilterClass(String name) throws FilterException.FilterNotFoundException {
        if (!filters.containsKey(name)) {
            throw new FilterException.FilterNotFoundException();
        }

        return filters.get(name).getFilterClass();
    }

    /**
     * Creates an IFilter instance from the given name
     * @param name Name of the IFilter to create
     * @return The newly created IFilter
     * @throws FilterException.FilterCreationException
     * @throws FilterException.FilterNotFoundException
     */
    public IFilter createFilter(String name) throws FilterException.FilterCreationException, FilterException.FilterNotFoundException {
        Class<IFilter> filterClass = getFilterClass(name);

        try {
            return filterClass.newInstance();
        } catch (Exception e) {
            throw new FilterException.FilterCreationException(e);
        }
    }

    /**
     * Creates and loads an IFilter instance from the given name
     * @param name Name of the IFilter to create
     * @param filterConfig Json config
     * @return The newly created and loaded IFilter
     * @throws FilterException.FilterCreationException
     * @throws FilterException.FilterNotFoundException
     * @throws FilterException.FilterLoadException
     */
    public IFilter loadFilter(String name, JsonElement filterConfig) throws FilterException.FilterCreationException, FilterException.FilterNotFoundException, FilterException.FilterLoadException {
        IFilter filter = createFilter(name);
        filter.load(filterConfig);
        return filter;
    }

    /**
     * Gets the description of the {@link IFilter}
     * @param name
     * @return
     */
    public String getDesc(String name) {
        return filters.get(name).getDesc();
    }

    /**
     * Gets the value description of the {@link IFilter}
     * @param name
     * @return
     */
    public String getValueDesc(String name) {
        return filters.get(name).getValueDesc();
    }

    /**
     * Returns a List of all the names of the IFilters
     * @return
     */
    public List<String> getFilterNames() {
        List<String> ret = new ArrayList<String>();
        for (FilterWrapper wrapper : filters.values()) {
            ret.add(wrapper.getName());
        }
        return ret;
    }

    /**
     * Internal wrapper around an {@link IFilter} and its {@link Filter} annotation.
     */
    private class FilterWrapper {
        private Class<IFilter> filter;
        private Filter annot;

        public FilterWrapper(Class<IFilter> filter, Filter annot) {
            this.filter = filter;
            this.annot = annot;
        }

        /**
         * Returns the name of the {@link IFilter}
         * @return
         */
        public String getName() {
            return annot.name();
        }

        /**
         * Returns the description of the {@link IFilter}
         * @return
         */
        public String getDesc() {
            return annot.desc();
        }

        /**
         * Returns the value description of the {@link IFilter}
         * @return
         */
        public String getValueDesc() {
            return annot.valueDesc();
        }

        /**
         * Returns the {@link IFilter} Class
         * @return
         */
        public Class<IFilter> getFilterClass() {
            return filter;
        }

        /**
         * Creates the {@link IFilter}
         * @return
         * @throws IllegalAccessException
         * @throws InstantiationException
         */
        public IFilter createFilter() throws IllegalAccessException, InstantiationException {
            return filter.newInstance();
        }
    }
}
