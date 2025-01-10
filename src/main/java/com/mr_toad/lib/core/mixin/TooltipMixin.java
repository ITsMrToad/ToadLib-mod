package com.mr_toad.lib.core.mixin;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Tooltip.class)
public abstract class TooltipMixin {

    @Inject(method = "splitTooltip", at = @At("HEAD"), cancellable = true)
    private static void splitWithToadLib(Minecraft minecraft, Component c, CallbackInfoReturnable<List<FormattedCharSequence>> cir) {
        cir.setReturnValue(minecraft.font.split(c, ToadLib.CFG.tooltipLineLength.get()));
    }
}
