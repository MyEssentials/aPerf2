package aperf.modules.entity;

import aperf.APerf;
import aperf.api.util.EntityHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GrouperEvents {
    private int ticks = 0;

    @SubscribeEvent
    public void tick(TickEvent.WorldTickEvent worldTickEvent) {
        if (worldTickEvent.phase != TickEvent.Phase.END) return;
        if (ticks > 0) {
            ticks--;
            return;
        }

        World world = worldTickEvent.world;
        for (int i = 0; i < world.loadedEntityList.size(); i++) {
            Object o = world.loadedEntityList.get(i);
            if (Config.EntityGrouper.GroupItems && o instanceof EntityItem) {
                groupItem((EntityItem) o, world);
            } else if (Config.EntityGrouper.GroupExpOrbs && o instanceof EntityXPOrb) {
                groupExpOrbs((EntityXPOrb) o, world);
            }
        }

        ticks = MinecraftServer.getServer().worldServers.length * Config.EntityGrouper.SkipTicks;
    }

    private static void groupItem(EntityItem item, World world) {
        if (item.getEntityItem() == null || !item.getEntityItem().isStackable() || item.isDead) return;
        List<?> entities = world.getEntitiesWithinAABB(EntityItem.class, item.boundingBox.expand(Config.EntityGrouper.Range, Config.EntityGrouper.Range, Config.EntityGrouper.Range));
        for (Object o : entities) {
            EntityItem e = (EntityItem) o;
            if (e.isDead || e == item) continue;
            if (!item.combineItems(e)) continue;
            EntityHelper.removeEntity(item.isDead ? item : e);
            APerf.LOG.info("Grouped item " + e.getEntityItem().getDisplayName());
            return;
        }
    }

    private static void groupExpOrbs(EntityXPOrb xpOrb, World world) {
        if (xpOrb == null || xpOrb.isDead) return;
        List<?> entities = world.getEntitiesWithinAABB(EntityXPOrb.class, xpOrb.boundingBox.expand(Config.EntityGrouper.Range, Config.EntityGrouper.Range, Config.EntityGrouper.Range));
        List<EntityXPOrb> close = new ArrayList<EntityXPOrb>();
        for (Object o : entities) {
            EntityXPOrb e = (EntityXPOrb) o;
            if (e.isDead || e == xpOrb) continue;
            close.add(e);
        }
        int val = xpOrb.getXpValue(), cval = 0, age = xpOrb.xpOrbAge;
        for (EntityXPOrb orb : close) {
            cval = orb.getXpValue();
            if (val >= Integer.MAX_VALUE - cval) return;
            val += cval;
            age = Math.min(orb.xpOrbAge, age);
        }
        xpOrb.xpValue = val;
        xpOrb.xpOrbAge = age;

        for (EntityXPOrb orb : close) {
            orb.setDead();
            EntityHelper.removeEntity(orb);
        }
    }
}
