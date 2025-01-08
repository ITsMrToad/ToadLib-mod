package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * Used for opening files/paths/web-links
 * */
@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class LinkButton extends SpriteButton {

    public LinkButton(int x, int y, int w, int h, DefaultType type, File file) {
        this(x, y, w, h, type, b -> ToadClientUtils.openFile(file));
    }

    public LinkButton(int x, int y, int w, int h, DefaultType type, Path path) {
        this(x, y, w, h, type, b -> Util.getPlatform().openUri(path.toUri()));
    }

    public LinkButton(int x, int y, int w, int h, DefaultType type, URI uri) {
        this(x, y, w, h, type, b -> Util.getPlatform().openUri(uri));
    }

    public LinkButton(int x, int y, int w, int h, DefaultType type, OnPress press) {
        super(x, y, w, h, type.u(), type.v(), type::yDiff, type.w(), type.h(), 128, 128, press, c -> Component.translatable("toadlib.nar.open"), type.getTexture());
    }

    @Override
    public void renderTexture(GuiGraphics graphics, int x, int y) {
        graphics.pose().scale(1.5F, 1.5F, 1.5F);
        x -= 20;
        super.renderTexture(graphics, x, y);
    }

    @Override
    @Deprecated
    public final void setShouldRenderTemp(boolean shouldRenderTemp) {}

    @Override
    protected boolean isShouldRenderTemp() {
        return false;
    }

    public enum DefaultType {
        DEFAULT_FOLDER(0, 0, 15, 10),
        SAVES_FOLDER(20, 0, 15, 10),
        DATA_FOLDER(40, 0, 15, 10),
        RESOURCES_FOLDER(60,0, 15, 10),
        COMMON_FILE(25, 0, 7, 9),
        NBT_FILE(25, 0, 7, 9),
        JSON_FILE(25, 0, 7, 9),
        LINK(0, 116, 12, 12);

        public final int u;
        public final int v;
        public final int w;
        public final int h;

        DefaultType(int u, int v, int w, int h) {
            this.u = u;
            this.v = v;
            this.w = w;
            this.h = h;
        }

        public ResourceLocation getTexture() {
            return ToadClientUtils.ICONS;
        }

        public int u() {
            return this.u;
        }

        public int v() {
            return this.v;
        }

        public int w() {
            return this.w;
        }

        public int h() {
            return this.h;
        }

        public int yDiff(boolean hovered) {
            if (hovered) {
                if (this == COMMON_FILE || this == NBT_FILE || this == JSON_FILE) {
                    return 37;
                } else if (this == LINK) {
                    return 15;
                } else {
                    return 12;
                }
            } else {
                if (this == COMMON_FILE || this == NBT_FILE || this == JSON_FILE) {
                    return 25;
                } else {
                    return 0;
                }
            }
        }
    }
}
