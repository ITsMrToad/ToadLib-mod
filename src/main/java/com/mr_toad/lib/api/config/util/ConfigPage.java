package com.mr_toad.lib.api.config.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.core.ToadLib;
import net.minecraft.network.chat.Component;

public class ConfigPage extends ConfigEntry<Component, ConfigPage> {

    private final PageSeparateLine separateLine;

    public ConfigPage(Component name, PageSeparateLine separateLine) {
        super("page", name, null, ConfigEntryTypes.PAGE);
        this.separateLine = separateLine;
    }

    @Override
    public Component get() {
        String line = this.separateLine.getFormatted();

        int lineLength = line.length() / 2;

        Component lineLeft = Component.literal(line.substring(0, lineLength));
        Component lineRight = Component.literal(line.substring(lineLength));

        return lineLeft.copy().append(this.separateLine.requireSpace ? "" : " ").append(this.defaultValue).append(" ").append(lineRight);
    }

    @Override
    public boolean isDefault() {
        return true;
    }

    @Override
    public boolean drawInScreen() {
        return true;
    }

    @Override
    public Component getDescription() {
        return this.description;
    }

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final ConfigPage withWarning(HighlightWarning warning) {
        ToadLib.LOGGER.error(ToadLib.CONFIG, "Highlight warning cannot be added to ConfigPage!");
        return this;
    }

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final ConfigPage withPerformanceImpact(PerformanceImpact warning) {
        ToadLib.LOGGER.error(ToadLib.CONFIG, "Performance impact cannot be added to ConfigPage!");
        return this;
    }

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final ConfigPage dontDrawInScreen() {
        ToadLib.LOGGER.error(ToadLib.CONFIG, "Disabling drawInScreen makes ConfigPage useless!");
        return this;
    }

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final void setValue(Component value) {}

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final void resetValue() {}

    @Override
    public void save(JsonObject object) {}

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final void load(JsonElement element) {}

    ///@deprecated Not allowed for ConfigPage
    @Override
    @Deprecated(since = "1.0.0")
    public final Component getTitle() {
        throw new UnsupportedOperationException("Name is value");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof ConfigPage page) {
            return this.value.equals(page.value) && this.separateLine == page.separateLine;
        } else {
            return true;
        }
    }
}
