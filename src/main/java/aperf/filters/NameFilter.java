package aperf.filters;

import aperf.api.util.EntityHelper;
import aperf.api.util.TileEntityHelper;
import aperf.api.filter.Filter;
import aperf.api.filter.StringFilter;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

/**
 * Checks based on names
 */
@Filter(name = "Name", desc = "Checks based on names", valueDesc = "String")
public class NameFilter extends StringFilter {
    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            return this.value.equals(EntityHelper.getEntityName((Entity) o));
        }
        if (o instanceof TileEntity) {
            return this.value.equals(TileEntityHelper.getEntityName((TileEntity) o));
        }

        return false;
    }

    @Override
    public String group(Object o) {
        if (o instanceof Entity) {
            return EntityHelper.getEntityName((Entity) o);
        }
        if (o instanceof TileEntity) {
            return TileEntityHelper.getEntityName((TileEntity) o);
        }

        return null;
    }
}
