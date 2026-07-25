package com.mr_toad.lib.api.client.screen.config.widget;

import com.mr_toad.lib.api.client.screen.ex.widget.ExObjectSelectionList;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import org.jetbrains.annotations.NotNull;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ConfigEntriesSelectionList extends ExObjectSelectionList<ConfigEntriesSelectionList.Entry> {

    public ConfigEntriesSelectionList(Minecraft minecraft, int w, int h, int y, int ih) {
        super(minecraft, w, h, y, ih);
    }

    @Override
    public Optional<Entry> getEntryAt(double mx, double my) {
        return this.children().stream().filter(entry -> entry.isMouseOver(mx, my)).findFirst();
    }

    @Override
    protected void extractSelection(@NonNull GuiGraphicsExtractor graphics, @NonNull Entry entry, int outlineColor) {}

    @Environment(EnvType.CLIENT)
    public static class Entry extends ExObjectSelectionList.Entry<@NotNull Entry> {

        public final AbstractWidget widget;
        public final ConfigEntry<?, ?> entry;

        public Entry(AbstractWidget widget, ConfigEntry<?, ?> entry) {
            this.widget = widget;
            this.entry = entry;
        }

        @Override
        public @NotNull Component getNarration() {
            return CommonComponents.EMPTY;
        }

        @Override
        public void extractContent(@NonNull GuiGraphicsExtractor graphics, int mx, int my, boolean hovered, float pt) {
            this.widget.setY(this.getContentY());
            this.widget.extractRenderState(graphics, mx, my, pt);
        }

        @Override
        public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean bl) {
            return this.widget.mouseClicked(event, bl);
        }

        @Override
        public boolean mouseScrolled(double mx, double my, double delta, double d1) {
            return this.widget.mouseScrolled(mx, my, delta, d1);
        }

        @Override
        public boolean mouseDragged(@NotNull MouseButtonEvent event, double dragX, double dragY) {
            return this.widget.mouseDragged(event, dragX, dragY);
        }

        @Override
        public boolean mouseReleased(@NotNull MouseButtonEvent event) {
            return this.widget.mouseReleased(event);
        }

        @Override
        public boolean keyPressed(@NotNull KeyEvent event) {
            return this.widget.keyPressed(event);
        }

        @Override
        public boolean charTyped(@NotNull CharacterEvent event) {
            return this.widget.charTyped(event);
        }

        @Override
        public boolean keyReleased(@NotNull KeyEvent event) {
            return this.widget.keyReleased(event);
        }

        @Override
        public boolean isMouseOver(double mx, double my) {
            return this.widget.isMouseOver(mx, my);
        }
    }
}
