package io.github.myessentials.aperf.core.filter;

import com.flowpowered.math.vector.Vector3i;
import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.Entity;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ChunkFilter implements Filter {
    private int x1, z1;
    private int x2, z2;

    @Override
    public String getName() {
        return "Chunk";
    }

    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            Entity e = (Entity) o;
            Vector3i chunkPos = e.getLocation().getChunkPosition();
            return withinChunkPos(chunkPos.getX(), chunkPos.getZ());
        } else if (o instanceof TileEntity) {
            TileEntity te = (TileEntity) o;
            Vector3i chunkPos = te.getLocation().getChunkPosition();
            return withinChunkPos(chunkPos.getX(), chunkPos.getZ());
        }

        return false;
    }

    @Override
    public String group(Object o) {
        if (o instanceof Entity) {
            Entity e = (Entity) o;
            Vector3i chunkPos = e.getLocation().getChunkPosition();
            return String.format("%d:%d", chunkPos.getX(), chunkPos.getZ());
        } else if (o instanceof TileEntity) {
            TileEntity te = (TileEntity) o;
            Vector3i chunkPos = te.getLocation().getChunkPosition();
            return String.format("%d:%d", chunkPos.getX(), chunkPos.getZ());
        }

        return null;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        // Store the start points
        value.getNode("x1", x1).setValue(x1);
        value.getNode("z1", z1).setValue(z1);

        // Store the end points
        if (x1 != x2) value.getNode("x2", x2).setValue(x2);
        if (z1 != z2) value.getNode("z2", z2).setValue(z2);
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        // Load start points
        x1 = Optional.ofNullable(value.getNode("x1"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("x1 value is required"))
                .getInt();

        z1 = Optional.ofNullable(value.getNode("z1"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("z1 value is required"))
                .getInt();

        // Load end points
        x2 = value.getNode("x2").getInt(x1);
        z2 = value.getNode("z2").getInt(z1);

        // Check the given config
        checkConfig();
    }

    @Override
    public void deserialize(String value) throws ObjectMappingException {
        String[] parts = Optional.ofNullable(value)
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("Value must be given"))
                .split("/");

        // Load start points
        String[] startPoints = parts[0].split(",");
        if (startPoints.length != 2) throw new ObjectMappingException("Improperly formatted value");

        x1 = Integer.valueOf(Optional.ofNullable(startPoints[0])
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("x1 value must be given")));

        z1 = Integer.valueOf(Optional.ofNullable(startPoints[1])
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("z1 value must be given")));

        // Load end points
        if (parts.length == 2) {
            String[] endPoints = parts[1].split(",");
            x2 = endPoints.length >= 1 ? Integer.parseInt(endPoints[0]) : x1;
            z2 = endPoints.length >= 2 ? Integer.parseInt(endPoints[2]) : z1;
        } else {
            x2 = x1;
            z2 = z1;
        }

        // Check the given config
        checkConfig();
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

    public static class Type extends FilterType {
        @Override
        public String desc() {
            return "Checks the chunk position";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return ChunkFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Chunk";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Chunk";
        }
    }
}
