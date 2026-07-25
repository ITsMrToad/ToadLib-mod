package com.mr_toad.lib.api.client.screen.ex.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.input.MouseButtonEvent;
import org.jspecify.annotations.NonNull;

import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Optional;

public class ExObjectSelectionList<E extends ObjectSelectionList.Entry<@NotNull E>> extends ObjectSelectionList<@NotNull E> {

    public ExObjectSelectionList(Minecraft minecraftClient, int w, int h, int y, int ih) {
        super(minecraftClient, w, h, y, ih);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bl) {
        double mx = event.x();
        double my = event.y();
        this.updateScrolling(event);
        if (!this.isMouseOver(mx, my)) {
            return false;
        } else {
            return this.getEntryAt(mx, my).map(e -> {
                if (e.mouseClicked(event, bl)) {
                    E e1 = this.getFocused();
                    this.setFocused(e);
                    if (e1 != e && e1 instanceof ContainerEventHandler h) {
                        h.setFocused(null);
                    }
                    this.setDragging(true);
                    return true;
                } else {
                    return this.scrolling;
                }
            }).orElseGet(() -> this.scrolling);
        }
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        double mx = event.x();
        double my = event.y();
        return this.getEntryAt(mx, my).map(e -> e.mouseDragged(event, dragX, dragY)).orElse(super.mouseDragged(event, dragX, dragY));
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double deltaX, double deltaY) {
        return this.getEntryAt(mx, my).map(e -> e.mouseScrolled(mx, my, deltaX, deltaY)).orElse(super.mouseScrolled(mx, my, deltaX, deltaY));
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        double mx = event.x();
        double my = event.y();
        this.setDragging(false);
        if (!this.isMouseOver(mx, my)) {
            return false;
        } else {
            return this.getEntryAt(mx, my).map(e -> e.mouseReleased(event)).orElse(false);
        }
    }

    @Override
    public int addEntry(@NonNull E e, int h) {
        return super.addEntry(e, h);
    }

    @Override
    public void removeEntry(@NonNull E e) {
        super.removeEntry(e);
    }

    @Override
    public void clearEntries() {
        super.clearEntries();
    }

    public void addAll(Collection<E> entries) {
        entries.forEach(this::addEntry);
    }

    public Optional<E> getEntryAt(double mx, double my) {
        return Optional.ofNullable(this.getEntryAtPosition(mx, my));
    }
}
