package com.mr_toad.lib.api.client.resource;

import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigReloadListener extends SimplePreparableReloadListener<Void> {

    @Override
    protected Void prepare(ResourceManager manager, ProfilerFiller profilerFiller) {
        ToadLib.LOGGER.info("--ToadConfigs reload--");
        return null;
    }

    @Override
    protected void apply(Void v, ResourceManager manager, ProfilerFiller profiler) {
        ToadConfigs.getConfigs().forEach((id, cfg) -> {
            profiler.push("toad_config:" + id);
            cfg.save();
            profiler.pop();
        });
    }
}
