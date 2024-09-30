package com.mr_toad.lib.api.client.screen;

import com.mr_toad.lib.mtjava.ints.IntPair;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ParentableTitleableScreen<S extends Screen> extends TitleableScreen {

    public S parent;
    protected final IntPair turnBackButtonXY;

    protected Button turnBackButton;

    protected ParentableTitleableScreen(Component t, boolean centred, S parent) {
        this(t, centred, parent, IntPair.of(20, 20));
    }

    protected ParentableTitleableScreen(Component t, boolean centred, S parent, IntPair turnBackButtonXY) {
        super(t, centred);
        this.parent = parent;
        this.turnBackButtonXY = turnBackButtonXY;
    }

    @Override
    protected void init() {
        if (this.minecraft != null) {
            this.turnBackButton = this.addRenderableWidget(Button.builder(Component.literal("<-"), b -> this.minecraft.setScreen(this.parent)).bounds(this.turnBackButtonXY.getFirst(), this.turnBackButtonXY.getSecond(), 20, 20).build());
        }
    }
}
