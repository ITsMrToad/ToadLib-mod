package com.mr_toad.lib.api.client.screen.ex;

import com.mr_toad.lib.mtjava.math.vec.Vec2i;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ParentableToadLibScreen<S extends Screen> extends ToadLibScreen {

    protected static final Vec2i DEFAULT_BACK_BUTTON_POS = new Vec2i(20, 10);

    public S parent;
    protected final Vec2i backButtonPos;

    protected Button turnBackButton;

    protected ParentableToadLibScreen(Component t, S parent) {
        this(t, parent, DEFAULT_BACK_BUTTON_POS);
    }

    protected ParentableToadLibScreen(Component t, S parent, Vec2i backButtonPos) {
        super(t);
        this.parent = parent;
        this.backButtonPos = backButtonPos;
    }

    @Override
    protected void init() {
        this.turnBackButton = this.addRenderableWidget(Button.builder(Component.literal("<-"), b -> this.onTurnBack()).bounds(this.backButtonPos.x(), this.backButtonPos.y(), 20, 20).build());
        this.turnBackButton.setTooltip(Tooltip.create(CommonComponents.GUI_BACK));
    }

    protected void onTurnBack() {
        this.minecraft.setScreen(this.parent);
    }
}
