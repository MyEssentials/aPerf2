package aperf.filters;

import aperf.api.exceptions.FilterException;
import aperf.api.filter.Filter;
import aperf.api.filter.IFilter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

/**
 * Checks the chunk coordinates
 */
@Filter(name = "Where", desc = "Checks the chunk coordinates", valueDesc = "Chunk coords, <x>.<z>[/<x2>.<z2>]. Ex:-10.30")
public class WhereFilter implements IFilter {
    private int x1, z1, x2, z2;

    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            Entity e = (Entity) o;
            int cx = ((int) e.posX) >> 4;
            int cz = ((int) e.posZ) >> 4;
            return withinChunkPos(cx, cz);
        }
        if (o instanceof TileEntity) {
            TileEntity te = (TileEntity) o;
            int cx = te.xCoord >> 4;
            int cz = te.zCoord >> 4;
            return withinChunkPos(cx, cz);
        }

        return false;
    }

    @Override
    public String group(Object o) {
        if (o instanceof Entity) {
            Entity e = (Entity) o;
            int cx = ((int) e.posX) >> 4;
            int cz = ((int) e.posZ) >> 4;
            return String.format("%d:%d [%d:%d]", cx, cz, (cx << 4) + 8, (cz << 4) + 8);
        }
        if (o instanceof TileEntity) {
            TileEntity te = (TileEntity) o;
            int cx = te.xCoord >> 4;
            int cz = te.zCoord >> 4;
            // Whats with the extra bitshifts and adding 8?!? (Copied from old aperf)
            return String.format("%d:%d [%d:%d]", cx, cz, (cx << 4) + 8, (cz << 4) + 8);
        }
        return null;
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        if (json == null) throw new FilterException.FilterLoadException(this, "Json value can NOT be null!");
        try {
            if (json.isJsonObject()) {
                JsonObject o = json.getAsJsonObject();
                x1 = o.getAsJsonPrimitive("x1").getAsInt();
                z1 = o.getAsJsonPrimitive("z1").getAsInt();

                x2 = o.has("x2") ? o.getAsJsonPrimitive("x2").getAsInt() : x1;
                z2 = o.has("z2") ? o.getAsJsonPrimitive("z2").getAsInt() : z1;

                checkConfig();
            } else {
                throw new FilterException.FilterLoadException(this, "Invalid configuration! Should be an Object!");
            }
        } catch(NullPointerException e) {
            throw new FilterException.FilterLoadException(this, "Invalid configuration! Missing field!", e);
        } catch(ClassCastException e) {
            throw new FilterException.FilterLoadException(this, "Invalid configuration! Invalid field type!", e);
        }
    }

    @Override
    public void load(String str) throws FilterException.FilterLoadException {
        if (str == null) throw new FilterException.FilterLoadException(this, "String value can NOT be null!");
        String[] parts = str.split("/");
        String[] parts1 = parts[0].split(",");

        x1 = Integer.parseInt(parts1[0]);
        z1 = Integer.parseInt(parts1[2]);

        if (parts.length >= 2) {
            String[] parts2 = parts[0].split(",");
            x2 = parts2.length >= 1 ? Integer.parseInt(parts2[0]) : x1;
            z2 = parts2.length >= 2 ? Integer.parseInt(parts2[1]) : z1;
        }

        checkConfig();
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        JsonObject ret = new JsonObject();
        // Start Points
        ret.add("x1", new JsonPrimitive(x1));
        ret.add("z1", new JsonPrimitive(z1));
        // End Points
        if (x1 != x2) ret.add("x2", new JsonPrimitive(x2));
        if (z1 != z2) ret.add("z2", new JsonPrimitive(z2));
        return ret;
    }

    private void checkConfig() {
        // x1, z1 is ALWAYS less than x2, z2
        int temp;
        if (x2 < x1) {
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (z2 < z1) {
            temp = z1;
            z1 = z2;
            z2 = temp;
        }
    }

    private boolean withinChunkPos(int x, int z) {
        return (x >= x1 && x <= x2) && (z >= z1 && z <= z2);
    }
}
