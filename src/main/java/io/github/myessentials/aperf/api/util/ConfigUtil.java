package io.github.myessentials.aperf.api.util;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.gson.GsonConfigurationLoader;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.nio.file.Path;
import java.util.Optional;

public final class ConfigUtil {
    private ConfigUtil() {
    }

    /**
     * Returns a ConfigurationLoader based on the given path's file extension
     * @param path The path to check
     * @return The ConfigurationLoader created
     */
    public static Optional<ConfigurationLoader<? extends ConfigurationNode>> get(Path path) {
        if (path.toString().endsWith(".json")) {
            return Optional.ofNullable(GsonConfigurationLoader.builder().setPath(path).build());
        } else if (path.toString().endsWith(".yml") || path.toString().endsWith(".yaml")) {
            return Optional.ofNullable(YAMLConfigurationLoader.builder().setPath(path).build());
        } else if (path.toString().endsWith(".conf")) {
            return Optional.ofNullable(HoconConfigurationLoader.builder().setPath(path).build());
        }

        return Optional.empty();
    }
}
