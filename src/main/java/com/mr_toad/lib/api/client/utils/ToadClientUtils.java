package com.mr_toad.lib.api.client.utils;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.client.screen.config.ToadConfigsScreen;
import com.mr_toad.lib.api.client.screen.ex.widget.SpriteButton;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.core.mixin.access.TooltipAccessor;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Optional;

public class ToadClientUtils {

    public static final Component SEARCH = Component.translatable("toadlib.search");

    public static final ResourceLocation CONFIG = ToadLib.id("textures/gui/widget/toadconfig.png");
    public static final ResourceLocation ICONS = ToadLib.id("textures/gui/widget/icons.png");
    public static final ResourceLocation INFO = ToadLib.id("textures/gui/widget/info.png");
    public static final ResourceLocation RESET = ToadLib.id("textures/gui/widget/reset.png");
    public static final ResourceLocation REFRESH = ToadLib.id("textures/gui/widget/refresh.png");
    public static final ResourceLocation MAGNIFYING_GLASS = ToadLib.id("textures/gui/widget/mg_glass.png");

    public static final int RED = 16711680;
    public static final int ORANGE = 16744448;
    public static final int YELLOW = 16777045;
    public static final int GREEN = 5635925;
    public static final int LIGHT_BLUE = -1714618369;
    public static final int BLUE = 5592575;
    public static final int PURPLE = 10040319;
    public static final int DEFAULT = 4210752;
    public static final int DEFAULT_TEXT = 14737632;
    public static final int BLACK = 0;
    public static final int WHITE = 16777215;
    public static final int GRAY = 11184810;
    public static final int LIGHT_GRAY = 10526880;
    public static final int DARK_GREEN = 43520;

    public static ModelLayerLocation addMain(String modid, String name) {
        return register(modid, name, "main");
    }

    public static ModelLayerLocation register(String modid, String name, String layer) {
        return new ModelLayerLocation(new ResourceLocation(modid, name), layer);
    }

    public static SpriteButton createInfoButton(int x, int y, Button.OnPress press) {
        SpriteButton button = SpriteButton.of(INFO, press).size(x, y, 20, 20).uv(0, 0).texSize(20, 20).setYDiff(hoveredOrFocused -> hoveredOrFocused ? 20 : 0).imageSize(64, 64).build();
        button.setShouldRenderTemp(false);
        return button;
    }

    public static SpriteButton createResetButton(int x, int y, int w, int h, Button.OnPress press) {
        return SpriteButton.of(RESET, press).size(x, y, w, h).texSize(16, 16).imageSize(16, 16).buildCentred();
    }

    public static SpriteButton createRefreshButton(int x, int y, int w, int h, Button.OnPress press) {
        return SpriteButton.of(REFRESH, press).size(x, y, w, h).texSize(16, 16).imageSize(16, 16).buildCentred();
    }

    public static <S extends Screen> SpriteButton createConfigButton(int x, int y, S parent) {
        return SpriteButton.of(CONFIG, b -> Minecraft.getInstance().setScreen(new ToadConfigsScreen<>(parent))).size(x, y, 20, 20).imageSize(40, 40).texSize(21, 21).setYDiff(hoveredOrFocused -> hoveredOrFocused ? 21 : 0).setTooltip(Component.translatable("toadconfig.title")).buildCentred();
    }

    public static void renderMagnifyingGlass(GuiGraphics graphics, int x, int y, int w, int h) {
        graphics.blit(MAGNIFYING_GLASS, x, y, 0, 0, 0, 20, 20, w, h);
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
                ImmutableList<Component> list = ImmutableList.<Component>builder().add(((TooltipAccessor) tooltip).getMsg()).add(components).build();
                return Tooltip.create(CommonComponents.joinLines(list));
            }
        }
    }

    public static Tooltip extendTooltip(@Nullable Tooltip tooltip, Component c, boolean newLine) {
        if (tooltip == null) {
            return Tooltip.create(c);
        } else {
            Component original = ((TooltipAccessor) tooltip).getMsg();
            if (newLine) {
                return Tooltip.create(CommonComponents.joinLines(original, c));
            } else {
                return Tooltip.create(original.copy().append(c));
            }
        }
    }

    public static void openFile(File file) {
        Util.getPlatform().openUri(file.toURI());
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
