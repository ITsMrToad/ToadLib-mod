package com.mr_toad.lib.core.config;

import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.entry.BoolEntry;
import com.mr_toad.lib.api.config.entry.primitive.ShortEntry;
import com.mr_toad.lib.api.config.util.DeprecationRule;
import com.mr_toad.lib.api.config.util.HighlightWarning;
import com.mr_toad.lib.api.config.util.PerformanceImpact;
import com.mr_toad.lib.api.integration.IntegrationV2;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.network.chat.Component;

public class ToadLibConfig extends ToadConfig {

    private static final DeprecationRule<Boolean> MODMENU_IS_INSTALLED = DeprecationRule.make(IntegrationV2.MOD_MENU::isLoaded, Component.translatable("toadlib.config.showCfgButton.deprecation"), false);

    public final ShortEntry tooltipLineLength;
    public final BoolEntry showConfigButton;
    public final BoolEntry printDebug;

    public ToadLibConfig() {
        super(() -> "toadlib_config", ToadLib.MODID);
        this.tooltipLineLength = this.register(new ShortEntry("tooltipLineLength", (short) 280).range((short) 170, (short) 400).addTitle(Component.translatable("toadlib.config.tooltip_line_length")).addDescription(Component.translatable("toadlib.config.tooltip_line_length.tooltip"))).withPerformanceImpact(PerformanceImpact.LOW_INCREASE);
        this.showConfigButton = this.register(new BoolEntry("showCfgButton", true).addTitle(Component.translatable("toadlib.config.showCfgButton")).addDescription(Component.translatable("toadlib.config.showCfgButton.tooltip").append(Component.translatable("toadlib.config.showCfgButton.tooltip_2").withStyle(style -> style.withColor(-13108))))).withWarning(HighlightWarning.GAME_RELOAD).addDeprecationRule(MODMENU_IS_INSTALLED);
        this.printDebug = this.register(new BoolEntry("printDebug", false).addTitle(Component.translatable("toadlib.config.print_debug")));
    }

    @Override
    public Component title() {
        return Component.literal("ToadLib");
    }

}
