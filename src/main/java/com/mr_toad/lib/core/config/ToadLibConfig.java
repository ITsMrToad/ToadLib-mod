package com.mr_toad.lib.core.config;

import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.entry.BoolEntry;
import com.mr_toad.lib.api.config.entry.primitive.ShortEntry;
import com.mr_toad.lib.api.config.util.HighlightWarning;
import com.mr_toad.lib.api.config.util.PerformanceImpact;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ToadLibConfig extends ToadConfig {

    public final BoolEntry interpOverview = this.register(new BoolEntry("interp_overview", false).addTitle(Component.translatable("toadlib.config.interpolation_overview")).addDescription(Component.translatable("toadlib.config.interpolation_overview.tooltip")));
    public final ShortEntry tooltipLineLength = this.register(new ShortEntry("tooltipLineLength", (short) 280).range((short) 170, (short) 400).addTitle(Component.translatable("toadlib.config.tooltip_line_length")).addDescription(Component.translatable("toadlib.config.tooltip_line_length.tooltip"))).withWarning(HighlightWarning.GAME_RELOAD).withPerformanceImpact(PerformanceImpact.LOW_INCREASE);

    public ToadLibConfig() {
        super(() -> "toadlib_config");
    }

    @Override
    public Component title() {
        return Component.literal("ToadLib");
    }

}
