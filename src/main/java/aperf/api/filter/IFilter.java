package aperf.api.filter;

import aperf.api.exceptions.FilterException;
import com.google.gson.JsonElement;

/**
 * Interface for all Filters to build off of
 */
public interface IFilter {
    /**
     * Checks if o is a hit on the Filter.
     * Filter should type check the given Object and return false if it can't check it!
     * @param o The Object to check
     * @return If the Object hits the Filter
     */
    boolean hits(Object o);

    /**
     * Loads the Filter from the given JsonElement
     * @param json Json to load from
     * @throws FilterException.FilterLoadException
     */
    void load(JsonElement json) throws FilterException.FilterLoadException;

    /**
     * Loads the Filter from the given String.
     * Useful for creating Filters from commands
     * @param str String to load from
     * @throws FilterException.FilterLoadException
     */
    void load(String str) throws FilterException.FilterLoadException;

    /**
     * Save the filter as a JsonElement
     * @return The Filter as a JsonElement
     * @throws FilterException.FilterSaveException
     */
    JsonElement save() throws FilterException.FilterSaveException;
}
