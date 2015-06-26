package aperf.api.util;

import net.minecraft.tileentity.TileEntity;

/**
 */
public class TileEntityHelper {
    public static String getEntityName(TileEntity e) {
        String n = e.getBlockType().getLocalizedName();
        if (n.startsWith("tile.") && n.endsWith(".name")) {
            return e.getBlockType().getUnlocalizedName().substring("tile.".length());
        } else {
            return n;
        }
    }
}
