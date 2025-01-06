package com.mr_toad.lib.core.mixin.spark;

import com.mr_toad.lib.api.integration.BuiltInIntegrations;
import me.lucko.spark.common.command.modules.HealthModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HealthModule.class, remap = false)
public abstract class HealthModuleMixin {

    @Inject(method = "tps", at = @At("HEAD"))
    private static void onSparkTPSStart(CallbackInfo ci) {
        BuiltInIntegrations.SPARK_CLOSED = () -> false;
    }

    @Inject(method = "tps", at = @At("TAIL"))
    private static void onSparkTpsEnd(CallbackInfo ci) {
        BuiltInIntegrations.SPARK_CLOSED = () -> true;
    }
}
