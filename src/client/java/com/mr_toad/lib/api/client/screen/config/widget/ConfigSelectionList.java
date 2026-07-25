package com.mr_toad.lib.api.client.screen.config.widget;

import com.mr_toad.lib.api.client.screen.config.ToadConfigScreen;
import com.mr_toad.lib.api.client.screen.config.ToadConfigsScreen;
import com.mr_toad.lib.api.client.screen.ex.widget.ExObjectSelectionList;
import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.ToadConfigs;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ConfigSelectionList extends ExObjectSelectionList<ConfigSelectionList.Entry> {

    public final ToadConfigsScreen<?> parent;

    public ConfigSelectionList(Minecraft minecraft, int w, int h, int y, ToadConfigsScreen<?> parent) {
        super(minecraft, w, h, y, 20);
        this.parent = parent;
        ToadConfigs.getConfigs().values().stream().filter(ToadConfig::shouldCreateScreen).forEach(config -> this.addEntry(new Entry(this, config)));
    }

    public static class Entry extends ExObjectSelectionList.Entry<@NotNull Entry> {

        private final ConfigSelectionList owner;
        private final ToadConfig config;

        public Entry(ConfigSelectionList owner, ToadConfig config) {
            this.owner = owner;
            this.config = config;
        }

        @Override
        public @NotNull Component getNarration() {
            return this.config.title();
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            graphics.centeredText(Minecraft.getInstance().font, this.config.title(), this.owner.width / 2, this.getContentY() + 1, -1);
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean bl) {
            if (event.button() == 0) {
                Minecraft.getInstance().setScreen(new ToadConfigScreen(this.owner.parent, this.config));
                return true;
            }
            return false;
        }
    }
}
