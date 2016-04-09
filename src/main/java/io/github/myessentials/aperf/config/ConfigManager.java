package io.github.myessentials.aperf.config;

import io.github.myessentials.aperf.APerf;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;

public class ConfigManager {
    private ObjectMapper<Config> configObjectMapper;
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode configRoot;
    public Config config;

    public ConfigManager() {
        try {
            configObjectMapper = ObjectMapper.forClass(Config.class);
        } catch (ObjectMappingException e) {
            APerf.getLogger().error("Failed to make config object mapper", e);
        }
    }

    public boolean load() {
        APerf.getLogger().info("Loading configuration");
        try {
            configRoot = configLoader.load();
            config = configObjectMapper.bindToNew().populate(configRoot);
        } catch (IOException | ObjectMappingException e) {
            APerf.getLogger().error("Failed to load config", e);
            return false;
        }
        return true;
    }

    public boolean save() {
        APerf.getLogger().info("Saving configuration");
        try {
            configObjectMapper.bind(config).serialize(configRoot);
            configLoader.save(configRoot);
        } catch (IOException | ObjectMappingException e) {
            APerf.getLogger().error("Failed to save config", e);
            return false;
        }
        return true;
    }

    public void setLoader(ConfigurationLoader<CommentedConfigurationNode> loader) {
        this.configLoader = loader;
    }
}
