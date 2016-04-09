package io.github.myessentials.aperf.registry;

import com.google.common.collect.ImmutableList;
import io.github.myessentials.aperf.api.filter.FilterRegistration;
import io.github.myessentials.aperf.core.filter.*;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class FilterRegistry implements AdditionalCatalogRegistryModule<FilterRegistration> {
    private final Map<String, FilterRegistration> filterMappings = new HashMap<>();

    @Override
    public Optional<FilterRegistration> getById(String id) {
        return Optional.ofNullable(filterMappings.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<FilterRegistration> getAll() {
        return ImmutableList.copyOf(filterMappings.values());
    }

    @Override
    public void registerAdditionalCatalog(FilterRegistration extraCatalog) {
        filterMappings.put(checkNotNull(extraCatalog).getId().toLowerCase(), extraCatalog);
    }

    @Override
    @DelayedRegistration(RegistrationPhase.INIT)
    public void registerDefaults() {
        registerAdditionalCatalog(new AllFilter.Registration());
        registerAdditionalCatalog(new ChunkFilter.Registration());
        registerAdditionalCatalog(new ClassFilter.Registration());
        registerAdditionalCatalog(new DimensionFilter.Registration());
        registerAdditionalCatalog(new HashFilter.Registration());
        registerAdditionalCatalog(new InstanceFilter.Registration());
        registerAdditionalCatalog(new LClassFilter.Registration());
        registerAdditionalCatalog(new MultiFilter.Registration());
        registerAdditionalCatalog(new NameFilter.Registration());
        registerAdditionalCatalog(new PositionFilter.Registration());
    }
}
