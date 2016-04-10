package io.github.myessentials.aperf.registry;

import com.google.common.collect.ImmutableList;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.core.filter.*;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class FilterRegistry implements AdditionalCatalogRegistryModule<FilterType> {
    private final Map<String, FilterType> filterMappings = new HashMap<>();

    @Override
    public Optional<FilterType> getById(String id) {
        return Optional.ofNullable(filterMappings.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<FilterType> getAll() {
        return ImmutableList.copyOf(filterMappings.values());
    }

    @Override
    public void registerAdditionalCatalog(FilterType extraCatalog) {
        filterMappings.put(checkNotNull(extraCatalog).getId().toLowerCase(), extraCatalog);
    }

    @Override
    @DelayedRegistration(RegistrationPhase.INIT)
    public void registerDefaults() {
        registerAdditionalCatalog(new AllFilter.Type());
        registerAdditionalCatalog(new ChunkFilter.Type());
        registerAdditionalCatalog(new ClassFilter.Type());
        registerAdditionalCatalog(new DimensionFilter.Type());
        registerAdditionalCatalog(new HashFilter.Type());
        registerAdditionalCatalog(new InstanceFilter.Type());
        registerAdditionalCatalog(new LClassFilter.Type());
        registerAdditionalCatalog(new MultiFilter.Type());
        registerAdditionalCatalog(new NameFilter.Type());
        registerAdditionalCatalog(new PositionFilter.Type());
    }
}
