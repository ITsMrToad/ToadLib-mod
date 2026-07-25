package com.mr_toad.lib.api.client.utils.graphics;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.mtjava.MtJava;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2fStack;

import java.util.function.Consumer;

public class Graphics2D {

    public static void point(GuiGraphicsExtractor graphics, int x, int y) {
        point(graphics, x, y, MtJava.rgb2Argb(ToadClientUtils.BLACK));
    }

    public static void point(GuiGraphicsExtractor graphics, int x, int y, int color) {
        popPush(graphics.pose(), _ -> {
            graphics.horizontalLine(x, x + 5, y, -1);
            graphics.horizontalLine(x, x + 5, y + 5, -1);
            graphics.verticalLine(x, y, y + 5, -1);
            graphics.verticalLine(x + 5, y, y + 5, -1);
            fill(graphics, x + 1, y + 1, 4, 4, color);
        });
    }

    public static void drawIdealText(GuiGraphicsExtractor graphics, Component text, Font font, GuiEventListener widget, boolean hover, int x, int y, int w, int h) {
        GuiGraphicsExtractor.HoveredTextEffects hoveredTextEffects;
        if (hover) {
            if (text.getStyle().getClickEvent() != null) {
                hoveredTextEffects = GuiGraphicsExtractor.HoveredTextEffects.TOOLTIP_AND_CURSOR;
            } else {
                hoveredTextEffects = GuiGraphicsExtractor.HoveredTextEffects.TOOLTIP_ONLY;
            }
        } else {
            hoveredTextEffects = GuiGraphicsExtractor.HoveredTextEffects.NONE;
        }
        ActiveTextCollector collector = widget instanceof AbstractWidget wid ? graphics.textRendererForWidget(wid, hoveredTextEffects) : graphics.textRenderer(hoveredTextEffects);
        int j = font.width(text);
        int l = y + (h - 9) / 2;
        boolean bl = j > w;
        if (bl) {
            collector.acceptScrollingWithDefaultCenter(text, x + 2, x + w - 2, y, y + h);
        } else {
            collector.accept(x, l, text.getVisualOrderText());
        }
    }

    public static void drawIdealText(GuiGraphicsExtractor graphics, Component text, Font font, int x, int y, int w, int h) {
        ActiveTextCollector collector = graphics.textRenderer();
        int j = font.width(text);
        int l = y + (h - 9) / 2;
        boolean bl = j > w;
        if (bl) {
            collector.acceptScrollingWithDefaultCenter(text, x + 2, x + w - 2, y, y + h);
        } else {
            collector.accept(x, l, text.getVisualOrderText());
        }
    }

    public static void fill(GuiGraphicsExtractor graphics, int x, int y, int w, int h, int color) {
        graphics.fill(x, y, x + w, y + h, color);
    }

    public static void fill(GuiGraphicsExtractor graphics, RenderPipeline type, int x, int y, int w, int h, int color) {
        graphics.fill(type, x, y, x + w, y + h, color);
    }

    public static void fillGradient(GuiGraphicsExtractor graphics, int x, int y, int w, int h, int startColor, int endColor) {
        graphics.fillGradient(x, y, x + w, y + h, startColor, endColor);
    }

    public static void scissor(GuiGraphicsExtractor graphics, int x, int y, int w, int h, Runnable runnable) {
        graphics.enableScissor(x, y, x + w, y + h);
        runnable.run();
        graphics.disableScissor();
    }

    public static void popPush(Matrix3x2fStack stack, Consumer<Matrix3x2fStack> action) {
        stack.pushMatrix();
        action.accept(stack);
        stack.popMatrix();
    }

    public static void safePopPush(Matrix3x2fStack stack, Consumer<Matrix3x2fStack> action) {
        stack.pushMatrix();
        try {
            action.accept(stack);
        } finally {
            stack.popMatrix();
        }
    }
}


