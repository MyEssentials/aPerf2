package io.github.myessentials.aperf;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import io.github.myessentials.aperf.api.filter.Filter;
import io.github.myessentials.aperf.api.filter.FilterType;
import io.github.myessentials.aperf.api.filter.FilterSerializer;
import io.github.myessentials.aperf.api.module.Module;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimit;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitRegistration;
import io.github.myessentials.aperf.api.spawnlimit.SpawnLimitSerializer;
import io.github.myessentials.aperf.cmd.Commands;
import io.github.myessentials.aperf.config.Config;
import io.github.myessentials.aperf.config.ConfigManager;
import io.github.myessentials.aperf.registry.FilterRegistry;
import io.github.myessentials.aperf.registry.ModuleRegistry;
import io.github.myessentials.aperf.registry.SpawnLimitRegistry;
import me.flibio.updatifier.Updatifier;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

import static io.github.myessentials.aperf.PluginInfo.*;

@Plugin(id = ID, name = NAME, version = VERSION, description = DESCRIPTION, authors = {"Alpha", "Legobear154"})
@Updatifier(repoName = REPO_NAME, repoOwner = REPO_OWNER, version = UPDATIFIER_VERSION)
public class APerf {
    public static APerf instance;

    public static Logger getLogger() {
        return instance.logger;
    }

    public static Config getConfig() {
        return instance.configManager.config;
    }

    public static Path getConfigDir() {
        return instance.configDir;
    }

    @Inject
    private Logger logger;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    private final ConfigManager configManager = new ConfigManager();

    @Listener
    public void onConstruction(GameConstructionEvent ev) {
        instance = this;

        // Register the TypeSerializers
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Filter.class), new FilterSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(SpawnLimit.class), new SpawnLimitSerializer());
    }

    @Listener
    public void onPreInit(GamePreInitializationEvent ev) {
        // Load the config
        configManager.load();

        // Register the registers
        Sponge.getRegistry().registerModule(FilterType.class, new FilterRegistry());
        Sponge.getRegistry().registerModule(Module.class, new ModuleRegistry());
        Sponge.getRegistry().registerModule(SpawnLimitRegistration.class, new SpawnLimitRegistry());
    }

    @Listener
    public void onInit(GameInitializationEvent ev) {
        // Register commands
        Commands.register(this, Sponge.getCommandManager());

        // Load all modules
        Sponge.getRegistry().getAllOf(Module.class).forEach(Module::load);
    }

    @Listener
    public void onPostInit(GamePostInitializationEvent ev) {
    }

    @Listener
    public void onStopped(GameStoppedEvent ev) {
        configManager.save();
    }

    @Inject
    private void setLoader(@DefaultConfig(sharedRoot = false) ConfigurationLoader<CommentedConfigurationNode> loader) {
        configManager.setLoader(loader);
    }
}
