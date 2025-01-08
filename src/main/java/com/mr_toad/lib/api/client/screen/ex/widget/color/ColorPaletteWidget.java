package com.mr_toad.lib.api.client.screen.ex.widget.color;

import com.mojang.blaze3d.platform.InputConstants;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.client.utils.graphics.Graphics2D;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.MtJava;
import com.mr_toad.lib.mtjava.ints.IntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.util.function.IntConsumer;

@ParametersAreNonnullByDefault
public class ColorPaletteWidget extends AbstractWidget {

    private static final ResourceLocation TEXTURE = ToadLib.id("textures/gui/widget/rgb_space.png");

    protected boolean alphaAllowed = true;
    @Nullable private IntPair currentPos = null;
    private int value;

    private final Minecraft minecraft;
    private final IntConsumer change;

    private Element<Integer> palette;
    private Element<Float> alpha;

    public ColorPaletteWidget(Minecraft minecraft, int x, int y, int value, IntConsumer change) {
        this(minecraft, x, y, value, CommonComponents.EMPTY, change);
    }

    public ColorPaletteWidget(Minecraft minecraft, int x, int y, int value, Component component, IntConsumer change) {
        super(x, y, 95, 95, component);
        this.minecraft = minecraft;
        this.change = change;
        this.value = value;
        this.palette = new Element<>(this.value, this.getX(), y - 10, 96, 64);
        this.alpha = new Element<>(1.0F, this.getX(), this.palette.y + this.palette.h + 5, 100, 15);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mx, int my, float pt) {
        this.palette.render((v, x, y, w, h, hovered) -> {
            Graphics2D.fill(graphics, x - 1, y - 1, w + 2, h + 2, hovered ? MtJava.rgb2Argb(ToadClientUtils.DEFAULT_TEXT) : MtJava.rgb2Argb(ToadClientUtils.DEFAULT));
            Graphics2D.scissor(graphics, x, y, w, h, () -> {
                graphics.blit(ColorPaletteWidget.TEXTURE, x, y, 0, 0, w, h, w, h);
                if (this.currentPos != null) {
                    Graphics2D.point(graphics, this.currentPos.getFirst(), this.currentPos.getSecond(), this.getValue());
                }
            });
        }, mx, my);

        if (this.alphaAllowed) {
            this.alpha.render((v, x, y, w, h, hovered) -> {
                graphics.drawCenteredString(this.minecraft.font, "Alpha: " + v, x + w / 2, y + h / 2 - 2, this.getFGColor());
                graphics.blitWithBorder(AbstractSliderButton.SLIDER_LOCATION, x, y, 0, hovered ? 20 : 0, w, h, 200, 20, 2, 3, 2, 2);
                graphics.blitWithBorder(AbstractSliderButton.SLIDER_LOCATION, x + (int) (v * (double) (w - 8)), y, 0, (!hovered ? 2 : 3) * 20, 8, h, 200, 20, 2, 3, 2, 2);
            }, mx, my);
        }

        int i = this.getX() + this.getWidth() + 5;
        int j = this.getY() + 5;

        graphics.fill(i - 1, j - 1, i + 16, j + 16, MtJava.rgb2Argb(ToadClientUtils.DEFAULT_TEXT));
        graphics.fill(i, j, i + 15, j + 15, MtJava.rgb2Argb(this.getValue()));

        if (this.getMessage() != CommonComponents.EMPTY) {
            graphics.drawCenteredString(this.minecraft.font, this.getMessage(), this.getX() + this.getWidth() / 2, this.getY(), ToadClientUtils.DEFAULT_TEXT);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (this.palette.isMouseOver(mx, my)) {
            return this.mousePaletteValue(mx, my);
        } else if (this.isOnAlpha(mx, my)) {
            this.alpha.setValue(this.sliderValueFromMouse(mx));
            this.onChange();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dragX, double dragY) {
        if (this.palette.isMouseOver(mx, my)) {
            return this.mousePaletteValue(mx, my);
        } else if (this.isOnAlpha(mx, my) && button == InputConstants.MOUSE_BUTTON_LEFT) {
            this.alpha.setValue(this.sliderValueFromMouse(mx));
            this.onChange();
            return true;
        }
        return false;
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        this.palette = new Element<>(this.value, this.getX(), y, 96, 64);
        this.alpha = new Element<>(1.0F, this.getX(), this.palette.y + this.palette.h + 5, 100, 15);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {}

    public void onChange() {
        this.updateValue();
        this.change.accept(this.value);
    }

    protected void updateValue() {
        int r = FastColor.ARGB32.red(this.palette.getValue());
        int g = FastColor.ARGB32.green(this.palette.getValue());
        int b = FastColor.ARGB32.blue(this.palette.getValue());
        int a = this.alphaAllowed ? Mth.floor(this.alpha.getValue() * 255.0F) : 255;
        this.value = FastColor.ARGB32.color(a, r, g, b);
    }

    protected float sliderValueFromMouse(double mx) {
        float value = (float) ((mx - (this.getX() + 4)) / (this.width - 8));
        value = Mth.lerp(Mth.clamp(value, 0.0F, 1.0F), 0.0F, 1.0F);
        value = (0.1F * Math.round(value / 0.1F));
        return Mth.map(value, 0.0F, 1.0F, 0.0F, 1.0F);
    }

    protected boolean mousePaletteValue(double mx, double my) {
        int mxp = Mth.floor(mx);
        int myp = Mth.floor(my);
        try  {
            int i = Graphics2D.getPixelRGB(mxp - this.palette.x, myp - this.palette.y, TEXTURE);
            this.palette.setValue(i);
            this.currentPos = IntPair.of(mxp, myp);
            this.onChange();
            return true;
        } catch (IOException e) {
            ToadLib.LOGGER.error("Error during opening palette listener!", e);
            return false;
        }
    }

    protected boolean isOnAlpha(double mx, double my) {
        return this.alphaAllowed && this.alpha.isMouseOver(mx, my);
    }

    public int getValue() {
        return this.value;
    }

    public Element<Integer> getPalette() {
        return this.palette;
    }

    public Element<Float> getAlpha() {
        return this.alpha;
    }

    public void setAlphaAllowed(boolean alphaAllowed) {
        this.alphaAllowed = alphaAllowed;
    }

    public static class Element<T> {
        private T value;

        private final int x;
        private final int y;
        private final int w;
        private final int h;

        public Element(T value, int x, int y, int w, int h) {
            this.value = value;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public void render(ElementRenderer<T> renderer, int mx, int my) {
            renderer.render(this.getValue(), this.x, this.y, this.w, this.h, this.isMouseOver(mx, my));
        }

        public boolean isMouseOver(double mx, double my) {
            return mx >= (double) this.x && my >= (double) this.y && mx < (double) (this.x + this.w) && my < (double) (this.y + this.h);
        }

        public void setValue(T value) {
            this.value = value;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getW() {
            return this.w;
        }

        public int getH() {
            return this.h;
        }

        public T getValue() {
            return this.value;
        }
    }

    @FunctionalInterface
    public interface ElementRenderer<T> {
        void render(T value, int x, int y, int w, int h, boolean hovered);
    }
}
