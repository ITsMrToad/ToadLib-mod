package com.mr_toad.lib.api.config.util;

import net.minecraft.network.chat.Component;

public enum HighlightWarning implements TooltipSuffix {

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

    @Override
    public Component tooltip() {
        return Component.translatable("toadconfig.warning").append(Component.translatable("toadconfig.warning." + this.name).withStyle(style -> style.withColor(this.getColor())));
    }
}
