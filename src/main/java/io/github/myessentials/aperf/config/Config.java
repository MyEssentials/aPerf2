package io.github.myessentials.aperf.config;

import com.google.common.collect.ImmutableList;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.List;

@ConfigSerializable
public class Config {
    @Setting(comment = "A list of modules to load. Use * to load all modules.")
    public List<String> loadedModules = ImmutableList.of("*");
}
