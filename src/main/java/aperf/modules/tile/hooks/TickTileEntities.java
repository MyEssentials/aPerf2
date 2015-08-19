package aperf.modules.tile.hooks;

import aperf.APerf;
import aperf.api.hooks.IEntityListHook;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.ForgeModContainer;

import java.util.Iterator;
import java.util.List;

public class TickTileEntities implements IEntityListHook<TileEntity> {
    @Override
    public void call(World world, List<TileEntity> toTick) {
        IChunkProvider chunkProvider = world.getChunkProvider();
        Iterator<TileEntity> iterator = toTick.iterator();
        int dim = world.provider.dimensionId;

        while (iterator.hasNext()) {
            TileEntity te = iterator.next();

            int x = te.xCoord;
            int y = te.yCoord;
            int z = te.zCoord;
            int cx = x >> 4;
            int cz = z >> 4;

            // TODO Allow Skipping Tick

            if (!te.isInvalid() && te.hasWorldObj() && chunkProvider.chunkExists(cx, cz)) {
                try {
                    te.updateEntity();
                } catch(Throwable throwable) {
                    CrashReport report = CrashReport.makeCrashReport(throwable, "Ticking Tile Entity");
                    CrashReportCategory crashReportCategory = report.makeCategory("Tile entity being ticked");
                    te.func_145828_a(crashReportCategory);
                    if (ForgeModContainer.removeErroringTileEntities) {
                        FMLLog.severe(report.getCompleteReport());
                        te.invalidate();
                        world.setBlockToAir(x, y, z);
                    } else {
                        APerf.LOG.debug(String.format("Tile Exception (%s, %s, %s), dim: %s, type: %s", x, y, z, dim, te.blockType.getUnlocalizedName()));
                        throw new ReportedException(report);
                    }
                }
            }

            if (te.isInvalid()) {
                iterator.remove();

                if (chunkProvider.chunkExists(cx, cz)) {
                    Chunk chunk = world.getChunkFromChunkCoords(cx, cz);

                    if (chunk != null) {
                        chunk.removeInvalidTileEntity(x & 15, y, z & 15);
                    }
                }
            }
        }
    }
}
