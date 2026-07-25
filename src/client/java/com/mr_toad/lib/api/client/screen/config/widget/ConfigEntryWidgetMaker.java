package com.mr_toad.lib.api.client.screen.config.widget;

import com.mr_toad.lib.api.client.screen.config.ToadConfigScreen;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;

@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface ConfigEntryWidgetMaker<T, E extends ConfigEntry<T, E>, W extends GuiEventListener & Renderable> {
    W make(ToadConfigScreen owner, int nextX, E entry);
}
