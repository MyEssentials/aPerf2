package aperf.modules.entity.hooks;

import aperf.APerf;
import aperf.api.hooks.IEntityListHook;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.ForgeModContainer;

import java.util.Iterator;
import java.util.List;

public class TickEntities implements IEntityListHook<Entity> {
    @Override
    public void call(World world, List<Entity> toTick) {
        IChunkProvider chunkProvider = world.getChunkProvider();
        Iterator<Entity> iterator = toTick.iterator();
        int dim = world.provider.dimensionId;

        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            double x = entity.posX;
            double y = entity.posY;
            double z = entity.posZ;
            int cx = (int) Math.floor(x) >> 4;
            int cz = (int) Math.floor(z) >> 4;

            // TODO Allow Skipping Tick

            if (!entity.isDead) {
                try {
                    world.updateEntity(entity);
                } catch(Throwable throwable) {
                    CrashReport report = CrashReport.makeCrashReport(throwable, "Ticking Entity");
                    CrashReportCategory crashReportCategory = report.makeCategory("Entity being ticked");
                    entity.addEntityCrashInfo(crashReportCategory);

                    if (ForgeModContainer.removeErroringEntities) {
                        FMLLog.severe(report.getCompleteReport());
                        world.removeEntity(entity);
                    } else {
                        APerf.LOG.debug(String.format("Entity Exception (%s, %s, %s), dim: %s, type: %s", x, y, z, dim, entity.getCommandSenderName()));
                        throw new ReportedException(report);
                    }
                }
            }

            if (entity.isDead) {
                if (entity.addedToChunk && chunkProvider.chunkExists(cx, cz)) {
                    world.getChunkFromChunkCoords(cx, cz).removeEntity(entity);
                }
                iterator.remove();
                world.onEntityRemoved(entity);
            }
        }
    }
}
