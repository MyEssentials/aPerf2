package io.github.myessentials.aperf.modules.spawnlimiter;

import com.google.common.collect.ImmutableList;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.List;

@ConfigSerializable
final class Config {
    @Setting(comment = "List of spawn event causes. Default should be fine for most.")
    public List<String> causesToCatch = ImmutableList.of("*");
}
