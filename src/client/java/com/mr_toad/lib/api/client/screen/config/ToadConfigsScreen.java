package com.mr_toad.lib.api.client.screen.config;

import com.mr_toad.lib.api.client.screen.config.widget.ConfigSelectionList;
import com.mr_toad.lib.api.client.screen.ex.ParentableToadLibScreen;
import com.mr_toad.lib.api.client.screen.ex.widget.LinkButton;
import com.mr_toad.lib.api.client.screen.ex.widget.SpriteButton;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.core.ToadLib;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Environment(EnvType.CLIENT)
public class ToadConfigsScreen<S extends Screen> extends ParentableToadLibScreen<S> {

    private static final Component TITLE = Component.translatable("toadconfig.title");
    private static final Component REFRESH = Component.translatable("toadconfig.refresh_tooltip");
    private static final Component OPEN = Component.translatable("toadconfig.open_folder");

    public ConfigSelectionList configSelectionList;
    public SpriteButton refreshButton;
    public LinkButton folderButton;

    public ToadConfigsScreen(S parent) {
        super(TITLE, parent);
        if (ToadLib.printDebug()) {
            ToadLib.LOGGER.info(ToadLib.CONFIG, "Found '{}' configs", ToadConfigs.getConfigs().size());
        }
    }

    @Override
    protected void init() {
        this.configSelectionList = this.addRenderableWidget(new ConfigSelectionList(this.minecraft, this.width + 70, this.height, 33, this));
        this.refreshButton = this.addRenderableWidget(ToadClientUtils.createRefreshButton(40, 10, 20, 20, _ -> this.minecraft.reloadResourcePacks()));
        this.refreshButton.setTooltip(Tooltip.create(REFRESH));
        this.folderButton = this.addRenderableWidget(new LinkButton(73, 7, 20, 20, LinkButton.DefaultType.DATA_FOLDER, FabricLoader.getInstance().getConfigDir()));
        this.folderButton.setTooltip(Tooltip.create(OPEN));
        super.init();
    }
}
