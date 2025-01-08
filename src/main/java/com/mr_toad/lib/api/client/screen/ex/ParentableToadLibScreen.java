package com.mr_toad.lib.api.client.screen.ex;

import com.mr_toad.lib.mtjava.ints.IntPair;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ParentableToadLibScreen<S extends Screen> extends ToadLibScreen {

    public S parent;
    protected final IntPair turnBackButtonXY;

    protected Button turnBackButton;

    protected ParentableToadLibScreen(Component t, boolean centred, S parent) {
        this(t, centred, parent, IntPair.of(20, 10));
    }

    protected ParentableToadLibScreen(Component t, boolean centred, S parent, IntPair turnBackButtonXY) {
        super(t, centred);
        this.parent = parent;
        this.turnBackButtonXY = turnBackButtonXY;
    }

    @Override
    protected void init() {
        this.turnBackButton = this.addRenderableWidget(Button.builder(Component.literal("<-"), b -> this.onTurnBack()).bounds(this.turnBackButtonXY.getFirst(), this.turnBackButtonXY.getSecond(), 20, 20).build());
        this.turnBackButton.setTooltip(Tooltip.create(CommonComponents.GUI_BACK));
    }

    protected void onTurnBack() {
        this.getMinecraft().setScreen(this.parent);
    }
}
