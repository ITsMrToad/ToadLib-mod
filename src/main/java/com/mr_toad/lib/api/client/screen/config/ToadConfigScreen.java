package com.mr_toad.lib.api.client.screen.config;

import com.mr_toad.lib.api.client.init.ConfigEntryWidgetsRegistry;
import com.mr_toad.lib.api.client.screen.config.widget.ConfigEntryWidgetMaker;
import com.mr_toad.lib.api.client.screen.ex.ParentableToadLibScreen;
import com.mr_toad.lib.api.client.screen.ex.widget.ExEditBox;
import com.mr_toad.lib.api.client.screen.ex.widget.LinkButton;
import com.mr_toad.lib.api.client.screen.ex.widget.SpriteButton;
import com.mr_toad.lib.api.client.screen.ex.widget.WidgetSelectionList;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryType;
import com.mr_toad.lib.mtjava.collections.execute.ExecutableArrayList;
import com.mr_toad.lib.mtjava.ints.IntPair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class ToadConfigScreen extends ParentableToadLibScreen<Screen> {

    private static final Component RESET = Component.translatable("toadconfig.reset");

    private final ExecutableArrayList tickable = new ExecutableArrayList();

    public final ToadConfig config;

    public WidgetSelectionList widgetSelectionList;
    public ExEditBox searchBox;
    public SpriteButton resetButton;
    public LinkButton fileButton;

    public String lastSearch = "";

    public ToadConfigScreen(Screen parent, ToadConfig config) {
        super(CommonComponents.EMPTY, true, parent, IntPair.of(20, 7));
        this.config = config;
    }

    @Override
    protected void init() {
        this.widgetSelectionList = this.addRenderableWidget(new WidgetSelectionList(this.getMinecraft(), this.width, this.height, 30, this.height - 20, 35));

        this.fillEntries(e -> true, true);

        Component component = Component.translatable("toadconfig.search", ToadConfigs.getConfigTitle(this.config)).withStyle(ChatFormatting.GRAY);

        this.searchBox = this.addRenderableWidget(new ExEditBox(this.font, this.width / 2 - 40, 7, 140, 20, component));
        this.searchBox.setHint(component);
        this.searchBox.setResponder(s -> {
            if (!Objects.equals(this.lastSearch, s)) {
                this.tickable.clear();
                this.fillEntries(e -> e.getTitle().getString().contains(s), this.lastSearch.isEmpty());
                this.lastSearch = s;
            }
        });
        this.searchBox.setRenderMagnifyingGlass(true);

        this.resetButton = this.addRenderableWidget(ToadClientUtils.createResetButton(40, 7, 20, 20, b -> {
            this.tickable.clear();
            this.config.getEntries().forEach(ConfigEntry::resetValue);
            this.resetButton.active = !this.config.getEntries().stream().allMatch(ConfigEntry::isDefault);
        }));
        this.resetButton.setTooltip(Tooltip.create(RESET));

        this.fileButton = this.addRenderableWidget(new LinkButton(65, 7, 20, 20, LinkButton.DefaultType.COMMON_FILE, this.config.getConfig()));
        this.fileButton.setTooltip(Tooltip.create(Component.translatable("toadconfig.open_file", ToadConfigs.getConfigTitle(this.config))));

        super.init();
    }

    @Override
    public void tick() {
        this.tickable.run();
        this.searchBox.tick();
        super.tick();
    }

    @Override
    protected void onTurnBack() {
        super.onTurnBack();
        this.config.save();
    }

    @Override
    public void onClose() {
        super.onClose();
        this.config.save();
    }

    @SuppressWarnings("rawtypes")
    public void fillEntries(Predicate<ConfigEntry<?, ?>> filter, boolean drawPages) {
        int x = 15;
        this.widgetSelectionList.clearEntries();
        for (int i = 0; i < this.config.getEntries().stream().filter(filter).toList().size(); i++) {
            ConfigEntry<?, ?> entry = this.config.getEntries().get(i);
            ConfigEntryType type = entry.getType();
            if (!entry.drawInScreen() || type == ConfigEntryTypes.PAGE && !drawPages) {
                return;
            }
            Optional<ConfigEntryWidgetMaker<?, ?, ?>> optional = ConfigEntryWidgetsRegistry.getMakerOf(type);
            if (optional.isPresent()) {
                ConfigEntryWidgetMaker maker = optional.get();
                GuiEventListener listener = maker.make(this, x, 0, entry);
                ConfigEntryWidgetsRegistry.getTickerOf(type).ifPresent(consumer -> this.tickable.add(() -> consumer.accept(listener)));
                if (listener instanceof AbstractWidget widget) {
                    this.widgetSelectionList.addEntry(new WidgetSelectionList.Entry(widget));
                }
            }
        }
    }
}
