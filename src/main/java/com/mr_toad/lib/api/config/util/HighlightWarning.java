package com.mr_toad.lib.api.config.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum HighlightWarning {

    WORLD_RELOAD("world",-5374161),
    RESOURCE_RELOAD("resource",-8388480),
    GAME_RELOAD("game",-16776961);

    private final String name;
    private final int color;

    HighlightWarning(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public int getColor() {
        return this.color;
    }

    public Component getTooltip() {
        return Component.translatable("toadconfig.warning").append(Component.translatable("toadconfig.warning." + this.name).withStyle(Style.EMPTY.withColor(this.getColor())));
    }
}
