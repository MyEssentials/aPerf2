package io.github.myessentials.aperf.registry;

import com.google.common.collect.ImmutableList;
import io.github.myessentials.aperf.api.module.Module;
import io.github.myessentials.aperf.modules.entity.EntityModule;
import io.github.myessentials.aperf.modules.spawnlimiter.SpawnLimiterModule;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

public class ModuleRegistry implements AdditionalCatalogRegistryModule<Module> {
    private final Map<String, Module> moduleMappings = new HashMap<>();

    @Override
    public Optional<Module> getById(String id) {
        return Optional.ofNullable(moduleMappings.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<Module> getAll() {
        return ImmutableList.copyOf(moduleMappings.values());
    }

    @Override
    public void registerAdditionalCatalog(Module extraCatalog) {
        moduleMappings.put(checkNotNull(extraCatalog).getId().toLowerCase(), extraCatalog);
    }

    @Override
    @DelayedRegistration(RegistrationPhase.INIT)
    public void registerDefaults() {
        registerAdditionalCatalog(EntityModule.instance);
        registerAdditionalCatalog(SpawnLimiterModule.instance);
    }
}
