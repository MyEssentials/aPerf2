package io.github.myessentials.aperf.registry;

import com.google.common.collect.ImmutableList;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitRegistration;
import io.github.myessentials.aperf.core.limits.*;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class SpawnLimitRegistry implements AdditionalCatalogRegistryModule<SpawnLimitRegistration> {
    private final Map<String, SpawnLimitRegistration> spawnLimitMappings = new HashMap<>();

    @Override
    public Optional<SpawnLimitRegistration> getById(String id) {
        return Optional.ofNullable(spawnLimitMappings.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<SpawnLimitRegistration> getAll() {
        return ImmutableList.copyOf(spawnLimitMappings.values());
    }

    @Override
    public void registerAdditionalCatalog(SpawnLimitRegistration extraCatalog) {
        spawnLimitMappings.put(checkNotNull(extraCatalog).getId().toLowerCase(), extraCatalog);
    }

    @Override
    @DelayedRegistration(RegistrationPhase.INIT)
    public void registerDefaults() {
        registerAdditionalCatalog(new ChunkCount.Registration());
        registerAdditionalCatalog(new Disabled.Registration());
        registerAdditionalCatalog(new NearbyCount.Registration());
        registerAdditionalCatalog(new ServerCount.Registration());
        registerAdditionalCatalog(new WorldCount.Registration());
    }
}
