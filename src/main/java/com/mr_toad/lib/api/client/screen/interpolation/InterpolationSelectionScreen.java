package com.mr_toad.lib.api.client.screen.interpolation;

import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.api.client.init.EaseWidgetSettingsRegistry;
import com.mr_toad.lib.api.client.screen.ex.ParentableToadLibScreen;
import com.mr_toad.lib.api.client.screen.ex.widget.ExSlider;
import com.mr_toad.lib.api.client.screen.ex.widget.SpriteButton;
import com.mr_toad.lib.api.client.screen.interpolation.widget.InterpolationTypeWidget;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.mtjava.MtJava;
import com.mr_toad.lib.mtjava.collections.UniqueList;
import com.mr_toad.lib.mtjava.ints.IntPair;
import com.mr_toad.lib.mtjava.math.interpolation.HermiteInterpolation;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public abstract class InterpolationSelectionScreen<P extends Screen> extends ParentableToadLibScreen<P> {

    public static final ImmutableList<Interpolation> ALL_REGISTERED_INTERPOLATIONS = ImmutableList.copyOf(Interpolations.INTERPOLATIONS.sortByID().iterator());

    private static final Component INTERPS = Component.translatable("toadlib.screen.interp.interpolations");
    private static final Component FROM_INFO = Component.translatable("toadlib.screen.interp.info").withStyle(ChatFormatting.UNDERLINE).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://easings.net")));
    private static final Component COMMON_INFO = Component.translatable("toadlib.screen.interp.info1");
    private static final Component ADDITIONAL_INFO = Component.translatable("toadlib.screen.interp.info2");

    protected final List<Interpolation> interpolations = new UniqueList<>();
    protected final List<InterpolationTypeWidget> widgets = new UniqueList<>();

    private float a = 1.0F;
    private float b = 1.0F;
    private float c = 1.0F;
    private float d = 1.0F;

    private float bias = 0.0F;
    private HermiteInterpolation.Tension tension = HermiteInterpolation.Tension.DEFAULT;

    protected ExSlider aSlider;
    protected ExSlider bSlider;
    protected ExSlider cSlider;
    protected ExSlider dSlider;

    protected ExSlider biasSlider;
    protected CycleButton<HermiteInterpolation.Tension> tensionCycle;

    protected SpriteButton info;

    protected Interpolation selected;

    public InterpolationSelectionScreen(List<Interpolation> interpolations, @Nullable P parent) {
        super(INTERPS, true, parent, IntPair.of(10, 10));
        this.interpolations.addAll(interpolations);
        this.selected = interpolations.get(0);
    }

    @Override
    protected void init() {
        super.init();

        this.aSlider = this.addRenderableWidget(this.createSlider("A", this.a, 0, s -> this.a = (float) s.getValue()));
        this.bSlider = this.addRenderableWidget(this.createSlider("B", this.b, 20, s -> this.b = (float) s.getValue()));
        this.cSlider = this.addRenderableWidget(this.createSlider("C", this.c, 40, s -> this.c = (float) s.getValue()));
        this.dSlider = this.addRenderableWidget(this.createSlider("D", this.d, 60, s -> this.d = (float) s.getValue()));

        this.biasSlider = this.addRenderableWidget(new ExSlider(this.width / 2 + 60, this.height / 2 + 25, 140, 20, Component.translatable("toadlib.screen.hermite.bias"), CommonComponents.EMPTY, -1.0D, 1.0D, this.bias, 0.1D, b -> {
            double v = b.getValue();
            if (this.bias != v) {
                this.bias = (float) v;
            }
        }));

        this.tensionCycle = this.addRenderableWidget(CycleButton.builder(HermiteInterpolation.Tension::symbol).withInitialValue(this.tension).withValues(HermiteInterpolation.Tension.values()).create(this.width / 2 + 60, this.height / 2 + 45, 75, 20, Component.translatable("toadlib.screen.hermite.tension"), (b, v) -> {
            if (this.tension != v) {
                this.tension = v;
            }
        }));

        this.info = this.addRenderableOnly(ToadClientUtils.createInfoButton(35, 10, b -> {}));
        this.info.setTooltip(Tooltip.create(CommonComponents.joinLines(COMMON_INFO, ADDITIONAL_INFO)));

        if (this.selected instanceof HermiteInterpolation hermiteInterpolation) {
            hermiteInterpolation.setBias(this.bias);
            hermiteInterpolation.setTension(this.tension);
        }

        this.fillWidgets();
        this.updateWidgetAccess();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        graphics.drawCenteredString(this.font, FROM_INFO, this.width / 2 + 60, this.height - 10, MtJava.rgb2Argb(ToadClientUtils.DEFAULT));
    }

    @Override
    protected boolean clickAtComponent(double mx, double my, int button) {
        return ToadClientUtils.getCentredStyleOf(this.font, FROM_INFO, mx, my, this.width / 2 + 80, this.height - 15).map(this::handleComponentClicked).orElse(false);
    }

    protected void fillWidgets() {
        if (this.interpolations.isEmpty()) {
            return;
        }

        int x = 10;
        int y = 35;

        for (Interpolation value : this.interpolations) {
            if (y > 230) {
                y = 35;
                x += 20;
            }

            InterpolationTypeWidget widget = new InterpolationTypeWidget(x, y, this.selected == value, b -> {
                this.widgets.stream().filter(w -> !w.isSame(value)).forEach(w -> w.selected = false);
                this.selected = value;
                this.onSelect(value);
                this.updateWidgetAccess();
            }, value);

            this.widgets.add(widget);
            this.addRenderableWidget(widget);
            y += 20;
        }
    }

    protected void updateWidgetAccess() {
        this.aSlider.active = false;
        this.bSlider.active = false;
        this.cSlider.active = false;
        this.dSlider.active = false;

        this.biasSlider.visible = false;
        this.tensionCycle.visible = false;

        if (this.selected != null) {
            EaseWidgetSettingsRegistry.resolve(this.aSlider, this.bSlider, this.cSlider, this.dSlider, this.selected);
            if (this.selected instanceof HermiteInterpolation) {
                this.biasSlider.visible = true;
                this.tensionCycle.visible = true;
            }
        }
    }

    public abstract void onSelect(Interpolation interpolation);

    protected ExSlider createSlider(String s, double target, int yOffset, ExSlider.OnValueChange onApply) {
        return new ExSlider(this.width / 2 + 75, this.height / 2 - 60 + yOffset, 140, 20, Component.literal(s + ":"), CommonComponents.EMPTY, 0.0D, 10.0D, target, 0.1D, onApply);
    }
}
