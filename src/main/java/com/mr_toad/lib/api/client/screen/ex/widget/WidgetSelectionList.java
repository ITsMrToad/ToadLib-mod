package com.mr_toad.lib.api.client.screen.ex.widget;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class WidgetSelectionList extends ExObjectSelectionList<WidgetSelectionList.Entry> {

    public WidgetSelectionList(Minecraft minecraft, int w, int h, int y0, int y1, int ih) {
        super(minecraft, w, h, y0, y1, ih);
        this.setRenderSelection(false);
    }

    @Override
    public int getRowWidth() {
        return this.width - 20;
    }

    @Override
    protected int getScrollbarPosition() {
        return super.getScrollbarPosition() + 90;
    }

    @Override
    protected int getRowTop(int index) {
        int y = super.getRowTop(0);
        for (int i = 0; i < index; i++) {
            y += this.children().get(i).widget.getHeight() + 3;
        }
        return y;
    }

    @Override
    public Optional<Entry> getEntryAt(double mx, double my) {
        return this.children().stream().filter(entry -> entry.isMouseOver(mx, my)).findFirst();
    }

    @OnlyIn(Dist.CLIENT)
    @MethodsReturnNonnullByDefault
    public static class Entry extends ObjectSelectionList.Entry<Entry> {

        private final AbstractWidget widget;

        public Entry(AbstractWidget widget) {
            this.widget = widget;
        }

        @Override
        public Component getNarration() {
            return CommonComponents.EMPTY;
        }

        @Override
        public void render(GuiGraphics graphics, int index, int rowTop, int rowBottom, int p_93527_, int p_93528_, int mx, int my, boolean hovered, float pt) {
            this.widget.setY(rowTop);
            this.widget.render(graphics, mx, my, pt);
        }

        @Override
        public boolean mouseClicked(double mx, double my, int button) {
            return this.widget.mouseClicked(mx, my, button);
        }

        @Override
        public boolean mouseScrolled(double mx, double my, double delta) {
            return this.widget.mouseScrolled(mx, my, delta);
        }

        @Override
        public boolean mouseDragged(double mx, double my, int button, double dragX, double dragY) {
            return this.widget.mouseDragged(mx, my, button, dragX, dragY);
        }

        @Override
        public boolean mouseReleased(double mx, double my, int button) {
            return this.widget.mouseReleased(mx, my, button);
        }

        @Override
        public boolean keyPressed(int button, int scan, int modifiers) {
            return this.widget.keyPressed(button, scan, modifiers);
        }

        @Override
        public boolean charTyped(char c, int i) {
            return this.widget.charTyped(c, i);
        }

        @Override
        public boolean keyReleased(int button, int scan, int modifiers) {
            return this.widget.keyReleased(button, scan, modifiers);
        }

        @Override
        public boolean isMouseOver(double mx, double my) {
            return this.widget.isMouseOver(mx, my);
        }
    }
}
