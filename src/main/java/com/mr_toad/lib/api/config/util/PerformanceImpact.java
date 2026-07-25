package com.mr_toad.lib.api.config.util;

import net.minecraft.network.chat.Component;

public enum PerformanceImpact implements TooltipSuffix {

    LOW_INCREASE("low_increase", -2031617),
    MEDIUM_INCREASE("medium_increase", -8388652),
    HIGH_INCREASE("high_increase", -5374161),
    VARIOUS_INCREASE("various_increase", 14524671),
    VERY_LOW_DECREASE("very_low_decrease", 14737631),
    LOW_DECREASE("low_decrease", -32),
    MEDIUM_DECREASE("medium_decrease", -23296),
    HIGH_DECREASE("high_decrease", -65536),
    EXTREME_DECREASE("extreme_decrease", -7667712),
    VARIOUS_DECREASE("various_decrease", 14524671);

    private final String name;
    private final int color;

    PerformanceImpact(String name, int color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public Component tooltip() {
        return Component.translatable("toadconfig.performance_impact").append(Component.translatable("toadconfig.performance_impact." + this.name).withStyle(style -> style.withColor(this.color)));
    }
}
