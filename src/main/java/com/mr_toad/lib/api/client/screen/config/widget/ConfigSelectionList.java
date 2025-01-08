package com.mr_toad.lib.api.client.screen.config.widget;

import com.mojang.blaze3d.platform.InputConstants;
import com.mr_toad.lib.api.client.screen.config.ToadConfigScreen;
import com.mr_toad.lib.api.client.screen.config.ToadConfigsScreen;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.ToadConfigs;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
public class ConfigSelectionList extends ObjectSelectionList<ConfigSelectionList.Entry> {

    public final ToadConfigsScreen<?> parent;

    public ConfigSelectionList(Minecraft minecraft, int w, int h, int y0, int y1, ToadConfigsScreen<?> parent) {
        super(minecraft, w, h, y0, y1, 20);
        this.parent = parent;
        ToadConfigs.getConfigs().values().stream().filter(ToadConfig::shouldCreateScreen).forEach(config -> this.addEntry(new Entry(this, config)));
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public static class Entry extends ObjectSelectionList.Entry<Entry> {

        private final ConfigSelectionList owner;
        private final ToadConfig config;

        public Entry(ConfigSelectionList owner, ToadConfig config) {
            this.owner = owner;
            this.config = config;
        }

        @Override
        public Component getNarration() {
            return ToadConfigs.getConfigTitle(this.config);
        }

        @Override
        public void render(GuiGraphics graphics, int x, int y, int w, int h, int y0, int mx, int my, boolean hovered, float partialTicks) {
            graphics.drawCenteredString(this.owner.parent.getMinecraft().font, ToadConfigs.getConfigTitle(this.config), this.owner.width / 2, y + 1, ToadClientUtils.WHITE);
        }

        @Override
        public boolean mouseClicked(double mx, double my, int button) {
            if (button == InputConstants.MOUSE_BUTTON_LEFT) {
                this.owner.minecraft.setScreen(new ToadConfigScreen(this.owner.parent, this.config));
                return true;
            }
            return false;
        }
    }

}
