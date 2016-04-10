package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.Entity;

import javax.annotation.Nonnull;
import java.util.UUID;

public class DimensionFilter extends StringFilter {
    private UUID worldUUID;

    @Override
    public String getName() {
        return "Dim";
    }

    @Override
    public boolean hits(Object o) {
        if (o instanceof Entity) {
            return ((Entity) o).getWorld().getUniqueId().equals(worldUUID);
        } else if (o instanceof TileEntity) {
            return ((TileEntity) o).getLocation().getExtent().getUniqueId().equals(worldUUID);
        }

        return false;
    }

    @Override
    public String group(Object o) {
        if (o instanceof Entity) {
            return ((Entity) o).getWorld().getUniqueId().toString();
        } else if (o instanceof TileEntity) {
            return ((TileEntity) o).getLocation().getExtent().getUniqueId().toString();
        }

        return null;
    }

    @Override
    public void deserialize(ConfigurationNode value) throws ObjectMappingException {
        // Deserialize the String
        super.deserialize(value);

        // Get UUID from string
        updateUUID();
    }

    @Override
    public void deserialize(String value) throws ObjectMappingException {
        super.deserialize(value);

        // Get UUID from string
        updateUUID();
    }

    private void updateUUID() throws ObjectMappingException {
        try {
            worldUUID = UUID.fromString(this.value);
        } catch(Exception e) {
            throw new ObjectMappingException("Given world ID is formatted incorrectly.", e);
        }
    }

    public static class Registration extends FilterRegistration {
        @Override
        public String desc() {
            return "Catches based on the dimension";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return DimensionFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Dim";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Dim";
        }
    }
}
