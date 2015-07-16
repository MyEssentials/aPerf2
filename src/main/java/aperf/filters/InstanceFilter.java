package aperf.filters;

import aperf.api.exceptions.FilterException;
import aperf.api.filter.Filter;
import aperf.api.filter.StringFilter;
import com.google.gson.JsonElement;

/**
 * Checks if its a class/superclass of the given
 */
@Filter(name = "Instance", desc = "Checks if its a class/superclass", valueDesc = "Full class name. Ex: net.minecraft.entity.item.EntityItem", isGrouper = false)
public class InstanceFilter extends StringFilter {
    private Class<?> clazz;

    @Override
    public boolean hits(Object o) {
        return o != null && clazz.isAssignableFrom(o.getClass());
    }

    @Override
    public String group(Object o) {
        return null;
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        super.load(json);
        try {
            clazz = Class.forName(this.value); // TODO Get de-obfuscated Entity and TileEntity class name
        } catch (ClassNotFoundException e) {
            throw new FilterException.FilterLoadException(this, e);
        }
    }
}
