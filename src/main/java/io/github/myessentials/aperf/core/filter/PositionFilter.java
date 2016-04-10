package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * {@link Filter} that checks based on objects position
 */
public class PositionFilter implements Filter {
    private int x1, y1, z1;
    private int x2, y2, z2;

    @Override
    public String getName() {
        return "Pos";
    }

    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            Location<World> location = ((Entity) o).getLocation();
            return withinPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        } else if (o instanceof TileEntity) {
            Location<World> location =((TileEntity) o).getLocation();
            return withinPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }

        return false;
    }

    @Override
    public String group(Object o) {
        if (o instanceof Entity) {
            Location<World> location = ((Entity) o).getLocation();
            return String.format("%d,%d,%d", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        } else if (o instanceof TileEntity) {
            Location<World> location =((TileEntity) o).getLocation();
            return String.format("%d,%d,%d", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }

        return null;
    }

    @Override
    public void serialize(ConfigurationNode value) throws ObjectMappingException {
        // Store the start points
        value.getNode("x1", x1).setValue(x1);
        value.getNode("y1", y1).setValue(y1);
        value.getNode("z1", z1).setValue(z1);

        // Store the end points
        if (x1 != x2) value.getNode("x2", x2).setValue(x2);
        if (y1 != y2) value.getNode("y2", y2).setValue(y2);
        if (z1 != z2) value.getNode("z2", z2).setValue(z2);
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        // Load start points
        x1 = Optional.ofNullable(value.getNode("x1"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("x1 value is required"))
                .getInt();

        y1 = Optional.ofNullable(value.getNode("y1"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("y1 value is required"))
                .getInt();

        z1 = Optional.ofNullable(value.getNode("z1"))
                .filter(node -> !node.isVirtual())
                .orElseThrow(() -> new ObjectMappingException("z1 value is required"))
                .getInt();

        // Load end points
        x2 = value.getNode("x2").getInt(x1);
        y2 = value.getNode("y2").getInt(y1);
        z2 = value.getNode("z2").getInt(z1);

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
        if (startPoints.length != 3) throw new ObjectMappingException("Improperly formatted value");

        x1 = Integer.valueOf(Optional.ofNullable(startPoints[0])
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("x1 value must be given")));

        y1 = Integer.valueOf(Optional.ofNullable(startPoints[1])
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("y1 value must be given")));

        z1 = Integer.valueOf(Optional.ofNullable(startPoints[2])
                .filter(s -> !s.isEmpty())
                .orElseThrow(() -> new ObjectMappingException("z1 value must be given")));

        // Load end points
        if (parts.length == 2) {
            String[] endPoints = parts[1].split(",");
            x2 = endPoints.length >= 1 ? Integer.parseInt(endPoints[0]) : x1;
            y2 = endPoints.length >= 2 ? Integer.parseInt(endPoints[1]) : y1;
            z2 = endPoints.length >= 3 ? Integer.parseInt(endPoints[2]) : z1;
        } else {
            x2 = x1;
            y2 = y1;
            z2 = z1;
        }

        // Check the given config
        checkConfig();
    }

    private void checkConfig() {
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

    private boolean withinPos(int x, int y, int z) {
        return (x >= x1 && x <= x2) && (y >= y1 && y <= y2) && (z >= z1 && z <= z2);
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Checks the position";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return PositionFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Pos";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Pos";
        }
    }
}
