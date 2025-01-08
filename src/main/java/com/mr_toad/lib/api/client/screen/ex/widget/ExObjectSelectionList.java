package com.mr_toad.lib.api.client.screen.ex.widget;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import org.jetbrains.annotations.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class ExObjectSelectionList<E extends ObjectSelectionList.Entry<E>> extends ObjectSelectionList<E> {

    @Nullable private Screen renderBgFromScreen = null;
    @Nullable private ResourceLocation bgLocation = null;

    public boolean isScrolling = false;

    public ExObjectSelectionList(Minecraft minecraft, int w, int h, int y0, int y1, int ih) {
        super(minecraft, w, h, y0, y1, ih);
    }

    @Override
    protected void renderBackground(GuiGraphics graphics) {
        if (this.renderBgFromScreen != null) {
            this.renderBgFromScreen.renderBackground(graphics);
        } else if (this.bgLocation != null) {
            graphics.blit(this.bgLocation, 0, 0, 0, 0.0F, 0.0F, this.width, this.height, 32, 32);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        this.updateScrollingState(mx, my, button);
        this.updateIsScrollingState(mx, button);
        if (!this.isMouseOver(mx, my)) {
            return false;
        } else {
            return this.getEntryAt(mx, my).map(e -> {
                if (e.mouseClicked(mx, my, button)) {
                    E e1 = this.getFocused();
                    this.setFocused(e);
                    if (e1 != e && e1 instanceof ContainerEventHandler containereventhandler) {
                        containereventhandler.setFocused(null);
                    }
                    this.setDragging(true);
                    return true;
                } else {
                    return this.isScrolling;
                }
            }).orElseGet(() -> {
                if (button == InputConstants.MOUSE_BUTTON_LEFT) {
                    this.clickedHeader((int)(mx - (double)(this.x0 + this.width / 2 - this.getRowWidth() / 2)), (int)(mx - (double)this.y0) + (int)this.getScrollAmount() - 4);
                    return true;
                } else {
                    return this.isScrolling;
                }
            });
        }
    }

    @Override
    public boolean mouseDragged(double mx, double my, int button, double dragX, double dragY) {
        return this.getEntryAt(mx, my).map(e -> e.mouseDragged(mx, my, button, dragX, dragY)).orElse(super.mouseDragged(mx, my, button, dragX, dragY));
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        return this.getEntryAt(mx, my).map(e -> e.mouseScrolled(mx, my, delta)).orElse(super.mouseScrolled(mx, my, delta));
    }

    @Override
    public boolean mouseReleased(double mx, double my, int button) {
        this.setDragging(false);
        if (!this.isMouseOver(mx, my)) {
            return false;
        } else {
            return this.getEntryAt(mx, my).map(e -> e.mouseReleased(mx, my, button)).orElse(false);
        }
    }

    @Override
    public int addEntry(E e) {
        return super.addEntry(e);
    }

    @Override
    public boolean removeEntry(E e) {
        return super.removeEntry(e);
    }

    @Override
    public void clearEntries() {
        super.clearEntries();
    }

    public void addAll(Collection<E> entries) {
        entries.forEach(this::addEntry);
    }

    @SafeVarargs
    public final void addAll(E... entries) {
        Collections.addAll(this.children(), entries);
    }

    public Optional<E> getEntryAt(double mx, double my) {
        return Optional.ofNullable(this.getEntryAtPosition(mx, my));
    }

    protected void updateIsScrollingState(double mx, int button) {
        this.isScrolling = button == 0 && mx >= this.getScrollbarPosition() && mx < this.getScrollbarPosition() + 6;
    }

    public void setCustomBackgroundTexture(@Nullable ResourceLocation bgLocation) {
        if (this.renderBgFromScreen != null) {
            this.renderBgFromScreen = null;
        }
        this.bgLocation = bgLocation;
    }

    public void setBackgroundFromScreen(@Nullable Screen renderBgFromScreen) {
        if (this.bgLocation != null) {
            this.bgLocation = null;
        }
        this.renderBgFromScreen = renderBgFromScreen;
    }
}
