package com.mr_toad.lib.api.config.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public enum PerformanceImpact {

    LOW_INCREASE("low_increase", -2031617),
    MEDIUM_INCREASE("medium_increase", -8388652),
    HIGH_INCREASE("high_increase", -5374161),
    LOW_DECREASE("low_decrease", -32),
    MEDIUM_DECREASE("medium_decrease", -23296),
    HIGH_DECREASE("high_decrease", -65536),
    EXTREME_DECREASE("extreme_decrease", -7667712);

    private final String name;
    private final int color;

    PerformanceImpact(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public Component getTooltip() {
        return Component.translatable("toadconfig.performance_impact").append(Component.translatable("toadconfig.performance_impact." + this.name).withStyle(Style.EMPTY.withColor(this.color)));
    }
}
