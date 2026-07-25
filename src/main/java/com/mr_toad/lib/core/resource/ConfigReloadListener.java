package com.mr_toad.lib.core.resource;

import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jspecify.annotations.NonNull;

import org.jetbrains.annotations.NotNull;

public class ConfigReloadListener extends SimplePreparableReloadListener<@NotNull Void> {

    @Override
    protected @NonNull Void prepare(@NotNull ResourceManager manager, @NotNull ProfilerFiller profilerFiller) {
        if (ToadLib.printDebug()) {
            ToadLib.LOGGER.debug("--ToadConfigs reload--");
        }
        return null;
    }

    @Override
    protected void apply(@NonNull Void v, @NotNull ResourceManager manager, @NotNull ProfilerFiller profiler) {
        ToadConfigs.getConfigs().forEach((id, cfg) -> cfg.save());
    }
}
