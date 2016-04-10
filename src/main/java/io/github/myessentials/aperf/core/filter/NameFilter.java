package io.github.myessentials.aperf.core.filter;

import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.filter.impl.StringFilter;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.entity.Entity;

import javax.annotation.Nonnull;

public class NameFilter extends StringFilter {
    @Override
    public String getName() {
        return "Name";
    }

    @Override
    public boolean hits(Object o) {
        CatalogType type;
        if (o instanceof Entity) {
            type = ((Entity) o).getType();
        } else if (o instanceof TileEntity) {
            type = ((TileEntity) o).getType();
        } else {
            return false;
        }

        return this.value.toLowerCase().equals(type.getId().toLowerCase());
    }

    @Override
    public String group(Object o) {
        CatalogType type;
        if (o instanceof Entity) {
            type = ((Entity) o).getType();
        } else if (o instanceof TileEntity) {
            type = ((TileEntity) o).getType();
        } else {
            return null;
        }

        return type.getId().toLowerCase();
    }

    public static class Type extends FilterType {
        @Override
        public String desc() {
            return "Checks based on names";
        }

        @Override
        public Class<? extends Filter> getFilterClass() {
            return NameFilter.class;
        }

        @Override
        @Nonnull
        public String getId() {
            return "Name";
        }

        @Override
        @Nonnull
        public String getName() {
            return "Name";
        }
    }
}
