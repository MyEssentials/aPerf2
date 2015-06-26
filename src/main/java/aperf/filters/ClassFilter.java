package aperf.filters;

import aperf.api.filter.Filter;
import aperf.api.filter.StringFilter;

/**
 * Checks the Class name
 */
@Filter(name = "Class", desc = "Checks the Class name", valueDesc = "Class name. Eg: EntityPig")
public class ClassFilter extends StringFilter {
    @Override
    public boolean hits(Object o) {
        // TODO Get de-obfuscated Entity and TileEntity class name
        return this.value.equals(o.getClass().getName());
    }
}
