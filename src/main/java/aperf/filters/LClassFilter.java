package aperf.filters;

import aperf.api.filter.Filter;
import aperf.api.filter.StringFilter;

/**
 * Checks the FULL Class name
 */
@Filter(name = "LClass", desc = "Checks the full class name", valueDesc = "Full Class name. Eg: net.minecraft.passive.EntityPig")
public class LClassFilter extends StringFilter {
    @Override
    public boolean hits(Object o) {
        // TODO Get de-obfuscated Entity and TileEntity simple class name
        return this.value.equals(o.getClass().getSimpleName());
    }

    @Override
    public String group(Object o) {
        return o.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return String.format("LClass(%s)", value);
    }
}
