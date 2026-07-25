package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

@Environment(EnvType.CLIENT)
public class ExEditBox extends EditBox {

    private boolean renderMagnifyingGlass = false;

    public ExEditBox(Font font, int x, int y, int w, int h) {
        this(font, x, y, w, h, CommonComponents.EMPTY);
    }

    public ExEditBox(Font font, int x, int y, int w, int h, Component component) {
        super(font, x, y, w, h, component);
    }

    @Override
    public void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mx, int my, float a) {
        super.extractWidgetRenderState(graphics, mx, my, a);
        if (this.renderMagnifyingGlass) {
            ToadClientUtils.renderMagnifyingGlass(graphics, this.getX() + this.getWidth() - 20, this.getY() + 3, 20, 20);
        }
    }

    public void setRenderMagnifyingGlass(boolean renderMagnifyingGlass) {
        this.renderMagnifyingGlass = renderMagnifyingGlass;
    }
}
