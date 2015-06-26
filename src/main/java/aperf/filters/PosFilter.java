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
 * Checks the entity position
 */
@Filter(name = "Pos", desc = "Checks the entity position", valueDesc = "Chunk coords, <x>.<z>[/<x2>.<z2>]. Ex:-10.30")
public class PosFilter implements IFilter {
    private int x1, y1, z1;
    private int x2, y2, z2;

    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            Entity e = (Entity) o;
            return withinPos((int) e.posX, (int) e.posY, (int) e.posZ);
        }
        if (o instanceof TileEntity) {
            TileEntity te = (TileEntity) o;
            return withinPos(te.xCoord, te.yCoord, te.zCoord);
        }

        return false;
    }

    @Override
    public void load(JsonElement json) throws FilterException.FilterLoadException {
        if (json.isJsonObject()) {
            JsonObject o = json.getAsJsonObject();
            x1 = o.getAsJsonPrimitive("x1").getAsInt();
            y1 = o.getAsJsonPrimitive("y1").getAsInt();
            z1 = o.getAsJsonPrimitive("z1").getAsInt();

            x2 = o.has("x2") ? o.getAsJsonPrimitive("x2").getAsInt() : x1;
            y2 = o.has("y2") ? o.getAsJsonPrimitive("y2").getAsInt() : y1;
            z2 = o.has("z2") ? o.getAsJsonPrimitive("z2").getAsInt() : z1;

            // x1, y1, z1 is ALWAYS less than x2, y2, z2
            int temp;
            if (x2 < x1) {
                temp = x1;
                x1 = x2;
                x2 = temp;
            }
            if (y2 < y1) {
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
            if (z2 < z1) {
                temp = z1;
                z1 = z2;
                z2 = temp;
            }
        }
    }

    @Override
    public JsonElement save() throws FilterException.FilterSaveException {
        JsonObject ret = new JsonObject();
        // Start Points
        ret.add("x1", new JsonPrimitive(x1));
        ret.add("y1", new JsonPrimitive(y1));
        ret.add("z1", new JsonPrimitive(z1));
        // End Points
        if (x1 != x2) ret.add("x2", new JsonPrimitive(x2));
        if (y1 != y2) ret.add("y2", new JsonPrimitive(y2));
        if (z1 != z2) ret.add("z2", new JsonPrimitive(z2));
        return ret;
    }

    private boolean withinPos(int x, int y, int z) {
        return (x >= x1 && x <= x2) && (y >= y1 && y <= y2) && (z >= z1 && z <= z2);
    }
}
