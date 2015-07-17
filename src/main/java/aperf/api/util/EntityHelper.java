package aperf.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.INpc;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class EntityHelper {
    private static Map<Class<?>, String> typeCache = new HashMap<Class<?>, String>();

    public static String getEntityName(Entity e) {
        String n = e.getCommandSenderName();
        if (n.startsWith("entity.") && n.endsWith(".name")) {
            return EntityList.getEntityString(e);
        } else {
            return n;
        }
    }

    // TODO Allow configuring this?
    public static String getEntityType(Entity e) {
        String n = typeCache.get(e.getClass());
        if (n != null) {
            return n;
        }

        if (e instanceof EntityGolem) {
            n = "Golem";
        }

        if (n == null) {
            for (Class<?> t : ClassHelper.getAllInterfaces(e.getClass())) {
                if (t.equals(IBossDisplayData.class)) {
                    n = "Boss";
                } else if (t.equals(IAnimals.class)) {
                    n = "Animal";
                } else if (t.equals(IMob.class)) {
                    n = "Monster";
                } else if (t.equals(IProjectile.class)) {
                    n = "Projectile";
                } else if (t.equals(INpc.class)) {
                    n = "NPC";
                }

                if (n != null) {
                    break;
                }
            }
        }

        if (n == null) {
            if (e instanceof EntityItem) {
                n = "Item";
            } else if (e instanceof EntityMob) {
                n = "Monster";
            } else if (e instanceof EntityPlayer) {
                n = "Player";
            } else if (e instanceof EntityFireball) {
                n = "Projectile";
            } else if (e instanceof EntityTNTPrimed) {
                n = "TNT";
            } else {
                n = "Unknown"; // e.getClass().getName();
            }
        }

        typeCache.put(e.getClass(), n);
        return n;
    }
}
