package com.mr_toad.lib.api.config.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public record TooltipSuffixV2(Component text, int color) implements TooltipSuffix {

    private static final MutableComponent REQUIRES = Component.translatable("toadconfig.warning");
    private static final MutableComponent PERFORMANCE_IMPACT = Component.translatable("toadconfig.performance_impact");

    //Reload warning
    public static final TooltipSuffixV2 WORLD_RELOAD = new TooltipSuffixV2(REQUIRES.copy().append(Component.translatable("toadconfig.warning.world")), -5374161);
    public static final TooltipSuffixV2 RESOURCE_RELOAD = new TooltipSuffixV2(REQUIRES.copy().append(Component.translatable("toadconfig.warning.resource")), -8388480);
    public static final TooltipSuffixV2 GAME_RELOAD = new TooltipSuffixV2(REQUIRES.copy().append(Component.translatable("toadconfig.warning.game")), -16776961);
    //Performance impact
    public static final TooltipSuffixV2 LOW_INCREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.low_increase")), -2031617);
    public static final TooltipSuffixV2 MEDIUM_INCREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.medium_increase")), -8388652);
    public static final TooltipSuffixV2 HIGH_INCREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.high_increase")), -5374161);
    public static final TooltipSuffixV2 LOW_DECREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.low_decrease")), -32);
    public static final TooltipSuffixV2 MEDIUM_DECREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.medium_decrease")), -23296);
    public static final TooltipSuffixV2 HIGH_DECREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.high_decrease")), -65536);
    public static final TooltipSuffixV2 EXTREME_DECREASE = new TooltipSuffixV2(PERFORMANCE_IMPACT.copy().append(Component.translatable("toadconfig.performance_impact.extreme_decrease")), -7667712);

    @Override
    public Component tooltip() {
        return this.text().copy().withStyle(Style.EMPTY.withColor(this.color()));
    }
}
