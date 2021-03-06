package aperf.filters;

import aperf.api.exceptions.FilterException;
import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks the dimension
 */
@Filter(name = "Dim", desc = "Checks the dimension", valueDesc = "Integer list, <n>[,<n>[,<n>...]]")
public class DimensionFilter implements IFilter {
    private final List<Integer> dimList = new ArrayList<Integer>();

    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            return dimList.contains(((Entity) o).worldObj.provider.dimensionId);
        }
        if (o instanceof TileEntity) {
            return dimList.contains(((TileEntity) o).getWorldObj().provider.dimensionId);
        }

        return false;
    }

    @Override
    public String group(Object o) {
        if (o instanceof Entity) {
            return String.valueOf(((Entity) o).worldObj.provider.dimensionId);
        }
        if (o instanceof TileEntity) {
            return String.valueOf(((TileEntity) o).getWorldObj().provider.dimensionId);
        }

        return null;
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        if (json == null) throw new FilterException.FilterLoadException(this, "Json value can NOT be null!");
        if (json.isJsonArray()) {
            for (JsonElement e : json.getAsJsonArray()) {
                if (e.isJsonPrimitive()) {
                    dimList.add(e.getAsInt());
                }
            }
        } else {
            throw new FilterException.FilterLoadException(this, "Invalid configuration! Should be an Array!");
        }
    }

    @Override
    public void load(String str) throws FilterException.FilterLoadException {
        if (str == null) throw new FilterException.FilterLoadException(this, "String value can NOT be null!");
        String[] dimStrs = str.split(",");
        for (String dimStr : dimStrs) {
            try {
                dimList.add(Integer.parseInt(dimStr));
            } catch(NumberFormatException e) {
                throw new FilterException.FilterLoadException(this, e);
            }
        }
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        JsonArray ret = new JsonArray();
        for (Integer i : dimList) {
            ret.add(new JsonPrimitive(i));
        }
        return ret;
    }

    @Override
    public String toString() {
        return String.format("Dim(%s)", dimList);
    }
}
