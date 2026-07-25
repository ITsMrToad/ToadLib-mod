package com.mr_toad.lib.api.config;

import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Optional;

public class ToadConfigs {

    private static final Object2ObjectMap<String, ToadConfig> CONFIGS = new Object2ObjectOpenHashMap<>();

    public static<C extends ToadConfig> void create(String id, C config) {
        CONFIGS.put(id, config);
        ToadLib.LOGGER.info(ToadLib.CONFIG, "Registered new config: '{}' for '{}'", config.path, id);
        config.load();
    }

    ///@deprecated Now, configs loads after created.
    @Deprecated(since = "idk")
    public static void load() {
        CONFIGS.values().forEach(ToadConfig::load);
    }

    public static Optional<ToadConfig> byId(String id) {
        return Optional.ofNullable(CONFIGS.get(id));
    }

    public static Object2ObjectMap<String, ToadConfig> getConfigs() {
        return CONFIGS;
    }
}
