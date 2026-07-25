package com.mr_toad.lib.api.client.screen.ex.widget.color;

import com.mojang.blaze3d.platform.NativeImage;
import com.mr_toad.lib.api.client.screen.ex.widget.CloseableWidget;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.client.utils.graphics.Graphics2D;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.MtJava;
import com.mr_toad.lib.mtjava.math.vec.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntConsumer;

public class ColorPaletteWidget extends AbstractWidget implements CloseableWidget {

    private static final Identifier SLIDER = Identifier.withDefaultNamespace("textures/gui/slider.png");
    private static final Identifier TEXTURE = ToadLib.id("textures/gui/widget/rgb_space.png");

    protected boolean alphaAllowed = true;
    @Nullable private Vec2i currentPos = null;
    private int value;

    private final Minecraft minecraft;
    private final IntConsumer change;
    private final PaletteTextureListener paletteTexture;

    private Element<Integer> palette;
    private Element<Float> alphaElement;

    public ColorPaletteWidget(Minecraft minecraft, int x, int y, int value, IntConsumer change) {
        this(minecraft, x, y, value, CommonComponents.EMPTY, change);
    }

    public ColorPaletteWidget(Minecraft minecraft, int x, int y, int value, Component component, IntConsumer change) {
        super(x, y, 95, 95, component);
        this.minecraft = minecraft;
        this.change = change;
        this.value = value;
        this.palette = new Element<>(this.value, this.getX(), y - 10, 96, 64);
        this.alphaElement = new Element<>(1.0F, this.getX(), this.palette.y + this.palette.h + 5, 100, 15);
        this.paletteTexture = PaletteTextureListener.create(TEXTURE);
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mx, int my, float pt) {
        this.palette.render((_, x, y, w, h, _) -> Graphics2D.scissor(graphics, x, y, w, h, () -> {
            graphics.blit(RenderPipelines.GUI_TEXTURED, ColorPaletteWidget.TEXTURE, x, y, 0, 0, w, h, w, h);
            if (this.currentPos != null) {
                Graphics2D.point(graphics, this.currentPos.x(), this.currentPos.y(), this.getValue());
            }
        }), mx, my);

        //Alpha editing is temporarily unavailable
        if (this.alphaAllowed) {
            this.alphaElement.render((_, _, _, _, _, _) -> {}, mx, my);
        }

        int i = this.getX() + this.getWidth() + 5;
        int j = this.getY() + 5;

        graphics.fill(i - 1, j - 1, i + 16, j + 16, MtJava.rgb2Argb(ToadClientUtils.DEFAULT_TEXT));
        graphics.fill(i, j, i + 15, j + 15, MtJava.rgb2Argb(this.getValue()));

        if (this.getMessage() != CommonComponents.EMPTY) {
            graphics.centeredText(this.minecraft.font, this.getMessage(), this.getX() + this.getWidth() / 2, this.getY(), ToadClientUtils.DEFAULT_TEXT);
        }
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bl) {
        double mx = event.x();
        double my = event.y();
        if (this.palette.isMouseOver(mx, my)) {
            return this.mousePaletteValue(mx, my);
        } else if (this.isOnAlpha(mx, my)) {
            this.alphaElement.setValue(this.sliderValueFromMouse(mx));
            this.onChange();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        double mx = event.x();
        double my = event.y();
        int button = event.button();
        if (this.palette.isMouseOver(mx, my)) {
            return this.mousePaletteValue(mx, my);
        } else if (this.isOnAlpha(mx, my) && button == 0) {
            this.alphaElement.setValue(this.sliderValueFromMouse(mx));
            this.onChange();
            return true;
        }
        return false;
    }

    @Override
    public void setY(int y) {
        super.setY(y);
        this.palette = new Element<>(this.value, this.getX(), y, 96, 64);
        this.alphaElement = new Element<>(1.0F, this.getX(), this.palette.y + this.palette.h + 5, 100, 15);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {}

    @Override
    public void onClose() {
        this.paletteTexture.close();
    }

    public void onChange() {
        this.updateValue();
        this.change.accept(this.value);
    }

    protected void updateValue() {
        int r = ARGB.red(this.palette.getValue());
        int g = ARGB.green(this.palette.getValue());
        int b = ARGB.blue(this.palette.getValue());
        int a = this.alphaAllowed ? Mth.floor(this.alphaElement.getValue() * 255.0F) : 255;
        this.value = ARGB.color(a, r, g, b);
    }

    protected float sliderValueFromMouse(double mx) {
        float v = (float) ((mx - (this.getX() + 4)) / (this.width - 8));
        v = Mth.lerp(Mth.clamp(v, 0.0F, 1.0F), 0.0F, 1.0F);
        v = (0.1F * Math.round(v / 0.1F));
        return Mth.map(v, 0.0F, 1.0F, 0.0F, 1.0F);
    }

    protected boolean mousePaletteValue(double mx, double my) {
        int i = this.paletteTexture.getColorAtMouse(mx, my, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        this.palette.setValue(i);
        this.currentPos = new Vec2i(Mth.floor(mx), Mth.floor(my));
        this.onChange();
        return true;
    }

    protected boolean isOnAlpha(double mx, double my) {
        return this.alphaAllowed && this.alphaElement.isMouseOver(mx, my);
    }

    public int getValue() {
        return this.value;
    }

    public Element<Integer> getPalette() {
        return this.palette;
    }

    public Element<Float> getAlphaElement() {
        return this.alphaElement;
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
            return mx >= this.x && my >= this.y && mx < (this.x + this.w) && my < (this.y + this.h);
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

    public record PaletteTextureListener(NativeImage image) {

        public static PaletteTextureListener create(Identifier identifier) {
            try (InputStream stream = Minecraft.getInstance().getResourceManager().open(identifier)) {
                return new PaletteTextureListener(NativeImage.read(stream));
            } catch (IOException _) {
                return null;
            }
        }

        public int getColorAtMouse(double mouseX, double mouseY, int widgetX, int widgetY, int widgetW, int widgetH) {
            double relX = mouseX - widgetX;
            double relY = mouseY - widgetY;

            if (relX < 0 || relX >= widgetW || relY < 0 || relY >= widgetH) {
                return -1;
            }

            int imgX = (int) ((relX / widgetW) * this.image.getWidth());
            int imgY = (int) ((relY / widgetH) * this.image.getHeight());
            imgX = Math.min(imgX, this.image.getWidth() - 1);
            imgY = Math.min(imgY, this.image.getHeight() - 1);

            int abgr = this.image.getPixel(imgX, imgY);

            int r = (abgr >> 16) & 0xFF;
            int g = (abgr >> 8) & 0xFF;
            int b = abgr & 0xFF;
            return (r << 16) | (g << 8) | b;
        }

        public void close() {
            if (this.image != null) {
                this.image.close();
            }
        }
    }

    @FunctionalInterface
    public interface ElementRenderer<T> {
        void render(T value, int x, int y, int w, int h, boolean hovered);
    }
}
