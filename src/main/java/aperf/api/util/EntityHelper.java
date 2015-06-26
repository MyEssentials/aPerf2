package aperf.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

/**
 */
public class EntityHelper {
    public static String getEntityName(Entity e) {
        String n = e.getCommandSenderName();
        if (n.startsWith("entity.") && n.endsWith(".name")) {
            return EntityList.getEntityString(e);
        } else {
            return n;
        }
    }
}
