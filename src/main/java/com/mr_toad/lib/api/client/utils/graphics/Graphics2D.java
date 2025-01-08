package com.mr_toad.lib.api.client.utils.graphics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.mtjava.MtJava;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class Graphics2D {

    public static int getPixelRGB(int x, int y, ResourceLocation location) throws IOException {
        try (InputStream stream = Minecraft.getInstance().getResourceManager().open(location)) {
            BufferedImage image = ImageIO.read(stream);
            return image.getRGB(x, y) & 0xFFFFFF;
        }
    }

    public static void point(GuiGraphics graphics, int x, int y) {
        point(graphics, x, y, MtJava.rgb2Argb(ToadClientUtils.BLACK));
    }

    public static void point(GuiGraphics graphics, int x, int y, int color) {
        popPush(graphics.pose(), stack -> {
            graphics.hLine(x, x + 5, y, MtJava.rgb2Argb(ToadClientUtils.DEFAULT));
            graphics.hLine(x, x + 5, y + 5, MtJava.rgb2Argb(ToadClientUtils.DEFAULT));
            graphics.vLine(x, y, y + 5, MtJava.rgb2Argb(ToadClientUtils.DEFAULT));
            graphics.vLine(x + 5, y, y + 5, MtJava.rgb2Argb(ToadClientUtils.DEFAULT));
            fill(graphics, x + 1, y + 1, 4, 4, color);
        });
    }

    public static void fill(GuiGraphics graphics, int x, int y, int w, int h, int color) {
        graphics.fill(x, y, x + w, y + h, color);
    }

    public static void fill(GuiGraphics graphics, RenderType type, int x, int y, int w, int h, int color) {
        graphics.fill(type, x, y, x + w, y + h, color);
    }

    public static void fillGradient(GuiGraphics graphics, int x, int y, int w, int h, int startColor, int endColor) {
        graphics.fillGradient(x, y, x + w, y + h, startColor, endColor);
    }

    public static void fillGradient(GuiGraphics graphics, RenderType type, int x, int y, int w, int h, int startColor, int endColor) {
        graphics.fillGradient(type, x, y, x + w, y + h, startColor, endColor, 0);
    }

    public static void scissor(GuiGraphics graphics, int x, int y, int w, int h, Runnable runnable) {
        graphics.enableScissor(x, y, x + w, y + h);
        runnable.run();
        graphics.disableScissor();
    }

    public static void popPush(PoseStack stack, Consumer<PoseStack> action) {
        stack.pushPose();
        action.accept(stack);
        stack.popPose();
    }
}


