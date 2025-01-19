package com.mr_toad.lib.core.mixin.catalogue;

import com.mr_toad.lib.api.integration.BuiltInIntegrations;
import com.mr_toad.lib.core.ToadLib;
import com.mrcrayfish.catalogue.client.ForgeModData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.forgespi.language.IModInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeModData.class, remap = false)
public abstract class ForgeModDataMixin {

    @Shadow public abstract String getModId();
    @Shadow @Final private IModInfo info;

    @Inject(method = "hasConfig", at = @At("TAIL"), cancellable = true)
    public void hasToadConfig(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && BuiltInIntegrations.hasToadConfig(this.getModId())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "openConfigScreen", at = @At("HEAD"), cancellable = true)
    public void openToadConfigScreen(Screen parent, CallbackInfo ci) {
        ConfigScreenHandler.getScreenFactoryFor(this.info).map((f) -> f.apply(Minecraft.getInstance(), parent)).ifPresentOrElse(newScreen -> Minecraft.getInstance().setScreen(newScreen), () -> {
            ToadLib.LOGGER.debug(ToadLib.CONFIG, "Trying to load config screen for catalogue...");
            BuiltInIntegrations.getConfigScreen(this.getModId(), parent).ifPresentOrElse(screen -> {
                ToadLib.LOGGER.debug(ToadLib.CONFIG, "Loaded config screen to catalogue for '{}'", this.getModId());
                Minecraft.getInstance().setScreen(screen);
            }, () -> ToadLib.LOGGER.debug(ToadLib.CONFIG, "Failed to load screen for catalogue of '{}'", this.getModId()));
        });
        ci.cancel();
    }
}
