package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class ExEditBox extends EditBox {

    private boolean renderMagnifyingGlass = false;

    public ExEditBox(Font font, int x, int y, int w, int h) {
        this(font, x, y, w, h, CommonComponents.EMPTY);
    }

    public ExEditBox(Font font, int x, int y, int w, int h, Component component) {
        super(font, x, y, w, h, component);
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mx, int my, float pt) {
        super.renderWidget(graphics, mx, my, pt);
        if (this.renderMagnifyingGlass) {
            ToadClientUtils.renderMagnifyingGlass(graphics, this.getX() + this.getWidth() - 20, this.getY() + 3, 20, 20);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (this.isMouseOver(mx, my)) {
            this.onClick(mx, my);
            return true;
        }
        return false;
    }


    public void setRenderMagnifyingGlass(boolean renderMagnifyingGlass) {
        this.renderMagnifyingGlass = renderMagnifyingGlass;
    }
}
