package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import it.unimi.dsi.fastutil.booleans.Boolean2IntFunction;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.util.function.IntUnaryOperator;

public class SpriteButton extends Button {

    protected final int u;
    protected final int v;
    protected final int textureW;
    protected final int textureH;
    protected final int imageW;
    protected final int imageH;
    protected final Identifier location;
    protected final Boolean2IntFunction yDiff;
    private boolean shouldRenderTemp = true;

    public static SpriteButton.Builder of(Identifier location, OnPress press) {
        return new Builder(location, press);
    }

    protected SpriteButton(int x, int y, int w, int h, int u, int v, Boolean2IntFunction yDiff, int textureW, int textureH, int imageW, int imageH, OnPress onPress, CreateNarration narration, Identifier location) {
        super(x, y, w, h, CommonComponents.EMPTY, onPress, narration);
        this.u = u;
        this.v = v;
        this.yDiff = yDiff != null ? yDiff : _ -> 0;
        this.textureW = textureW;
        this.textureH = textureH;
        this.imageW = imageW;
        this.imageH = imageH;
        this.location = location;
    }

    @Override
    protected void extractContents(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (this.isShouldRenderTemp()) {
            this.extractDefaultSprite(graphics);
        }
        this.renderTexture(graphics, this.getX(), this.getY());
    }

    public void renderTexture(GuiGraphicsExtractor graphics, int x, int y) {
        int drawX = x + (this.width  - this.textureW) / 2;
        int drawY = y + (this.height - this.textureH) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.location, drawX, drawY, this.u, (this.v + this.yDiff.apply(this.isHovered())), this.textureW, this.textureH, this.textureW, this.textureH, this.imageW, this.imageH, -1);
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
        private CreateNarration narration = Button.DEFAULT_NARRATION;
        private Boolean2IntFunction yDiff = hoveredOrFocused -> 0;
        private Component tooltip = CommonComponents.EMPTY;
        private final Identifier location;
        private final OnPress press;

        public Builder(Identifier location, OnPress press) {
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
            SpriteButton btn = new SpriteButton(this.x, this.y, this.w, this.h, this.u, this.v, this.yDiff, this.textureW, this.textureH, this.imageW, this.imageH, this.press, this.narration, this.location);
            if (this.tooltip != CommonComponents.EMPTY) {
                btn.setTooltip(Tooltip.create(this.tooltip));
            }
            return btn;
        }

        public SpriteButton buildWithMappingTexPos(IntUnaryOperator xp, IntUnaryOperator yp) {
            SpriteButton spriteButton = new SpriteButton(this.x, this.y, this.w, this.h, this.u, this.v, this.yDiff, this.textureW, this.textureH, this.imageW, this.imageH, this.press, this.narration, this.location) {
                @Override
                public void renderTexture(GuiGraphicsExtractor graphics, int x, int y) {
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

