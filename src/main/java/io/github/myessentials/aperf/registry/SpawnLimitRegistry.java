package io.github.myessentials.aperf.registry;

import com.google.common.collect.ImmutableList;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitType;
import io.github.myessentials.aperf.core.limits.*;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class SpawnLimitRegistry implements AdditionalCatalogRegistryModule<SpawnLimitType> {
    private final Map<String, SpawnLimitType> spawnLimitMappings = new HashMap<>();

    @Override
    public Optional<SpawnLimitType> getById(String id) {
        return Optional.ofNullable(spawnLimitMappings.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<SpawnLimitType> getAll() {
        return ImmutableList.copyOf(spawnLimitMappings.values());
    }

    @Override
    public void registerAdditionalCatalog(SpawnLimitType extraCatalog) {
        spawnLimitMappings.put(checkNotNull(extraCatalog).getId().toLowerCase(), extraCatalog);
    }

    @Override
    @DelayedRegistration(RegistrationPhase.INIT)
    public void registerDefaults() {
        registerAdditionalCatalog(ChunkCount.typeInstance);
        registerAdditionalCatalog(Disabled.typeInstance);
        registerAdditionalCatalog(NearbyCount.typeInstance);
        registerAdditionalCatalog(ServerCount.typeInstance);
        registerAdditionalCatalog(WorldCount.typeInstance);
    }
}
