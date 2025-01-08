package com.mr_toad.lib.api.config.error;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfigException extends UnsupportedOperationException {
    public ConfigException(String message) {
        super(message);
    }
}
