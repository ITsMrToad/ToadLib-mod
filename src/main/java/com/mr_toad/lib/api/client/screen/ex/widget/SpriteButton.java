package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.client.utils.graphics.Graphics2D;
import it.unimi.dsi.fastutil.booleans.Boolean2IntFunction;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.IntUnaryOperator;

@ParametersAreNonnullByDefault
public class SpriteButton extends Button {

    protected int u;
    protected int v;

    protected final int textureW;
    protected final int textureH;
    protected final int imageW;
    protected final int imageH;

    protected final ResourceLocation location;
    protected final Boolean2IntFunction yDiff;

    protected boolean shouldRenderTemp = true;

    public static SpriteButton.Builder of(ResourceLocation texture, OnPress press) {
        return new SpriteButton.Builder(texture, press);
    }

    protected SpriteButton(int x, int y, int w, int h, int u, int v, Boolean2IntFunction yDiff, int textureW, int textureH, int imageW, int imageH, OnPress onPress, Button.CreateNarration narration, ResourceLocation location) {
        super(x, y, w, h, CommonComponents.EMPTY, onPress, narration);
        this.imageW = imageW;
        this.imageH = imageH;
        this.u = u;
        this.v = v;
        this.yDiff = yDiff;
        this.textureW = textureW;
        this.textureH = textureH;
        this.location = location;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mx, int my, float partialTicks) {
        if (this.isShouldRenderTemp()) {
            super.renderWidget(graphics, mx, my, partialTicks);
        }
        Graphics2D.popPush(graphics.pose(), stack -> this.renderTexture(graphics, this.getX(), this.getY()));
    }

    public void renderTexture(GuiGraphics graphics, int x, int y) {
        graphics.blit(this.location, x, y, this.u, this.v, this.yDiff.apply(this.isHoveredOrFocused()), this.textureW, this.textureH, this.imageW, this.imageH);
    }

    public void setShouldRenderTemp(boolean shouldRenderTemp) {
        this.shouldRenderTemp = shouldRenderTemp;
    }

    protected boolean isShouldRenderTemp() {
        return this.shouldRenderTemp;
    }

    public static class Builder {

        private int u = 0;
        private int v = 0;
        private int x = 0;
        private int y = 0;
        private int w = 0;
        private int h = 0;
        private int textureW = 0;
        private int textureH = 0;
        private int imageW = 256;
        private int imageH = 256;

        private Boolean2IntFunction yDiff = hoveredOrFocused -> 0;
        private Button.CreateNarration narration = Button.DEFAULT_NARRATION;
        private Component tooltip = CommonComponents.EMPTY;

        private final ResourceLocation location;
        private final OnPress press;

        public Builder(ResourceLocation location, OnPress press) {
            this.location = location;
            this.press = press;
        }

        public Builder size(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            return this;
        }

        public Builder uv(int u, int v) {
            this.u = u;
            this.v = v;
            return this;
        }

        public Builder imageSize(int w, int h) {
            this.imageW = w;
            this.imageH = h;
            return this;
        }

        public Builder texSize(int w, int h) {
            this.textureW = w;
            this.textureH = h;
            return this;
        }

        public Builder setYDiff(Boolean2IntFunction diff) {
            this.yDiff = diff;
            return this;
        }

        public Builder setTooltip(Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder addNarration(CreateNarration narration) {
            this.narration = narration;
            return this;
        }

        public SpriteButton build() {
            SpriteButton spriteButton = new SpriteButton(this.x, this.y, this.w, this.h, this.u, this.v, this.yDiff, this.textureW, this.textureH, this.imageW, this.imageH, this.press, this.narration, this.location);
            if (this.tooltip != CommonComponents.EMPTY) {
                spriteButton.setTooltip(Tooltip.create(this.tooltip));
            }
            return spriteButton;
        }

        public SpriteButton buildWithMappingTexPos(IntUnaryOperator xp, IntUnaryOperator yp) {
            SpriteButton spriteButton = new SpriteButton(this.x, this.y, this.w, this.h, this.u, this.v, this.yDiff, this.textureW, this.textureH, this.imageW, this.imageH, this.press, this.narration, this.location) {
                @Override
                public void renderTexture(GuiGraphics graphics, int x, int y) {
                    super.renderTexture(graphics, xp.applyAsInt(x), yp.applyAsInt(y));
                }
            };
            if (this.tooltip != CommonComponents.EMPTY) {
                spriteButton.setTooltip(Tooltip.create(this.tooltip));
            }
            return spriteButton;
        }

        public SpriteButton buildCentred() {
            return this.buildWithMappingTexPos(xp -> ToadClientUtils.centred(xp, this.w, this.textureW), yp -> ToadClientUtils.centred(yp, this.h, this.textureH));
        }
    }
}
