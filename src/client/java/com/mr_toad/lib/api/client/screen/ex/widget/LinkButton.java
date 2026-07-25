package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.client.utils.graphics.Graphics2D;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * Used for opening files/paths/web-links
 * */
@Environment(EnvType.CLIENT)
public class LinkButton extends SpriteButton {

    private static final MutableComponent FILE_NAR = Component.translatable("toadlib.nar.open_file");
    private static final MutableComponent URL_NAR = Component.translatable("toadlib.nar.open_url");

    public LinkButton(int x, int y, int w, int h, DefaultType type, File file) {
        this(x, y, w, h, type, _ -> ToadClientUtils.openFile(file));
    }

    public LinkButton(int x, int y, int w, int h, DefaultType type, Path path) {
        this(x, y, w, h, type, _ -> ToadClientUtils.openURL(path.toUri()));
    }

    public LinkButton(int x, int y, int w, int h, DefaultType type, URI uri) {
        this(x, y, w, h, type, _ -> ToadClientUtils.openURL(uri));
    }

    public LinkButton(int x, int y, int w, int h, DefaultType type, OnPress press) {
        super(x, y, w, h, type.u(), type.v(), type::yDiff, type.w(), type.h(), 128, 128, press, _ -> type == DefaultType.LINK ? URL_NAR : FILE_NAR, type.getTexture());
        this.setShouldRenderTemp(false);
    }

    @Override
    protected void extractContents(@NonNull GuiGraphicsExtractor graphics, int mx, int my, float a) {
        Graphics2D.popPush(graphics.pose(), stack -> {
            stack.scale(1.6F);
            int sx = this.getX() - 30;
            int sy = this.getY() - 5;
            super.renderTexture(graphics, sx, sy);
        });
    }

    public enum DefaultType {
        DEFAULT_FOLDER(0, 0, 15, 10),
        SAVES_FOLDER(20, 0, 15, 10),
        DATA_FOLDER(40, 0, 15, 10),
        RESOURCES_FOLDER(60, 0, 15, 10),
        COMMON_FILE(20, 0, 7, 9),
        NBT_FILE(20, 0, 7, 9),
        JSON_FILE(20, 0, 7, 9),
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

        public Identifier getTexture() {
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
                return switch (this) {
                    case COMMON_FILE, NBT_FILE, JSON_FILE -> 37;
                    case LINK -> 15;
                    default -> 12;
                };
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
