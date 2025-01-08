package com.mr_toad.lib.api.config;

import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ToadConfigs {

    private static final Object2ObjectMap<String, ToadConfig> CONFIGS = new Object2ObjectOpenHashMap<>();

    public static<C extends ToadConfig> void create(String id, C config) {
        CONFIGS.put(id, config);
        ToadLib.LOGGER.info(ToadLib.CONFIG, "Registered new config: '{}' for '{}'", config.path, id);
    }

    public static void load() {
        CONFIGS.values().forEach(ToadConfig::load);
    }

    public static Optional<ToadConfig> byId(String id) {
        return Optional.ofNullable(CONFIGS.get(id));
    }

    public static Component getConfigTitle(ToadConfig config) {
        if (config.title() != CommonComponents.EMPTY) {
            return config.title();
        } else {
            return Component.literal(config.getConfig().toString());
        }
    }

    public static Object2ObjectMap<String, ToadConfig> getConfigs() {
        return CONFIGS;
    }
}
