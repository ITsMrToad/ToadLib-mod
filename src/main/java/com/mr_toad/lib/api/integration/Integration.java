package com.mr_toad.lib.api.integration;

import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.ToadConfigs;
import net.fabricmc.loader.api.FabricLoader;

import java.util.Optional;

///@deprecated This class used in hot-path, but not cache values.
@Deprecated(since = "1.4.5")
@FunctionalInterface
public interface Integration {

    String modid();

    default String findLocation(String s) {
        return this.modid() + ":" + s;
    }

    default boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded(this.modid());
    }

    default Optional<ToadConfig> getConfig() {
        return ToadConfigs.byId(this.modid());
    }

}
