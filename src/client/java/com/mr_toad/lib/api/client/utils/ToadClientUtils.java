package com.mr_toad.lib.api.client.utils;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.client.screen.config.ToadConfigsScreen;
import com.mr_toad.lib.api.client.screen.ex.widget.SpriteButton;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.net.URI;
import java.util.Optional;

public class ToadClientUtils {

    public static final Component SEARCH = Component.translatable("toadlib.search");

    public static final Identifier CONFIG = ToadLib.id("textures/gui/widget/toadconfig.png");
    public static final Identifier ICONS = ToadLib.id("textures/gui/widget/icons.png");
    public static final Identifier INFO = ToadLib.id("textures/gui/widget/info.png");
    public static final Identifier RESET = ToadLib.id("textures/gui/widget/reset.png");
    public static final Identifier REFRESH = ToadLib.id("textures/gui/widget/refresh.png");
    public static final Identifier MAGNIFYING_GLASS = ToadLib.id("textures/gui/widget/mg_glass.png");

    public static final int RED = 0xFFFF0000;
    public static final int ORANGE = 0xFFFF8000;
    public static final int YELLOW = 0xFFFFFF55;
    public static final int GREEN = 0xFF55FF55;
    public static final int LIGHT_BLUE = 0x99D7EFFF;
    public static final int BLUE = 0xFF5555FF;
    public static final int PURPLE = 0xFF9933FF;
    public static final int DEFAULT_TEXT = 0xFFFFFFFF;
    public static final int BLACK = 0xFF000000;
    public static final int WHITE = 0xFFFFFFFF;
    public static final int GRAY = 0xFFAAAAAA;
    public static final int LIGHT_GRAY = 0xFFA0A0A0;
    public static final int DARK_GREEN = 0xFF00AA00;

    public static SpriteButton createInfoButton(int x, int y, Button.OnPress press) {
        SpriteButton button = SpriteButton.of(INFO, press).size(x, y, 20, 20).uv(0, 0).texSize(20, 20).setYDiff(hoveredOrFocused -> hoveredOrFocused ? 20 : 0).imageSize(64, 64).build();
        button.setShouldRenderTemp(false);
        return button;
    }

    public static SpriteButton createResetButton(int x, int y, int w, int h, Button.OnPress press) {
        return SpriteButton.of(RESET, press).size(x, y, w, h).texSize(16, 16).imageSize(16, 16).build();
    }

    public static SpriteButton createRefreshButton(int x, int y, int w, int h, Button.OnPress press) {
        return SpriteButton.of(REFRESH, press).size(x, y, w, h).texSize(16, 16).imageSize(16, 16).build();
    }

    public static <S extends Screen> SpriteButton createConfigButton(int x, int y, S parent) {
        return SpriteButton.of(CONFIG, _ -> Minecraft.getInstance().setScreen(new ToadConfigsScreen<>(parent))).size(x, y, 20, 20).imageSize(40, 40).texSize(20, 20).setYDiff(hoveredOrFocused -> hoveredOrFocused ? 21 : 0).setTooltip(Component.translatable("toadconfig.title")).buildCentred();
    }

    public static void renderMagnifyingGlass(GuiGraphicsExtractor context, int x, int y, int w, int h) {
        context.blitSprite(RenderPipelines.GUI_TEXTURED, MAGNIFYING_GLASS, x, y, 0, 0, 0, 20, 20, w, h);
    }

    public static Tooltip extendTooltip(@Nullable Tooltip tooltip, Component... components) {
        if (tooltip == null) {
            if (components.length == 0) {
                ToadLib.LOGGER.error("Tooltip is null and length of extended elements are 0! Returned null.");
                return Tooltip.create(CommonComponents.EMPTY);
            } else {
                return Tooltip.create(CommonComponents.joinLines(components));
            }
        } else {
            if (components.length == 0) {
                return tooltip;
            } else {
                ImmutableList<@NotNull Component> list = ImmutableList.<Component>builder().add(tooltip.message).add(components).build();
                return Tooltip.create(CommonComponents.joinLines(list));
            }
        }
    }

    public static Tooltip extendTooltip(@Nullable Tooltip tooltip, Component c, boolean newLine) {
        if (tooltip == null) {
            return Tooltip.create(c);
        } else {
            Component original = tooltip.message;
            if (newLine) {
                return Tooltip.create(CommonComponents.joinLines(original, c));
            } else {
                return Tooltip.create(original.copy().append(c));
            }
        }
    }

    public static void openFile(File file) {
        Util.getPlatform().openFile(file);
    }

    public static void openURL(URI file) {
        Util.getPlatform().openUri(file);
    }

    public static int centred(int v1, int w1, int w2) {
        return v1 + (w1 / 2) - (w2 / 2);
    }

    public static Optional<Style> getCentredStyleOf(Font font, Component component, double mx, double my, int x, int y) {
        return getStyleOf(font, component, mx, my, x - font.width(component) / 2, y);
    }

    public static Optional<Style> getStyleOf(Font font, Component component, double mx, double my, int x, int y) {
        if (mx >= x && my >= y && mx < x + font.width(component) && my < y + font.lineHeight) {
            return Optional.of(component.getStyle());
        }
        return Optional.empty();
    }
}


