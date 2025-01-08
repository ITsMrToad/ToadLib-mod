package com.mr_toad.lib.api.config.entry;

import com.mojang.serialization.Codec;
import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.api.config.entry.type.ConfigEntryTypes;
import com.mr_toad.lib.api.config.error.ConfigException;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.MtJava;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntLists;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

public class ColorEntry extends ConfigEntry<Integer, ColorEntry> {

    private final Mode mode;

    private IntList cells = IntLists.emptyList();
    private boolean allowAlpha = true;
    private int cellSize = 10;

    @Deprecated //Don't use! Renders incorrect! For 'cells' Type.
    public ColorEntry(String name, int defaultColor, IntList list) {
        this(name, defaultColor, Mode.CELLS);
        this.cells = list;
    }

    public ColorEntry(String name, int defaultColor) {
        this(name, defaultColor, Mode.PALETTE);
    }

    private ColorEntry(String name, int defaultColor, Mode mode) {
        super(name, defaultColor, Codec.INT, ConfigEntryTypes.COLOR);
        this.mode = mode;
        try {
            this.validate(defaultColor);
        } catch (ConfigException e) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "Default value: '{}' of '{}' is invalid!", defaultColor, this, e);
            this.setValue(ToadClientUtils.WHITE);
        }
    }

    @Override
    public Integer get() {
        if (this.allowAlpha) {
            return super.get();
        } else {
            return FastColor.ARGB32.color(255, this.invokeRed(), this.invokeGreen(), this.invokeGreen());
        }
    }

    @Override
    public void setValue(Integer value) {
        try {
            this.validate(value);
            super.setValue(value);
        } catch (ConfigException e) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "Error parse color for '{}' to '{}'", this, value, e);
        }
    }

    @Override
    public Component getDescription() {
        Component original = super.getDescription();
        Component hex = Component.literal(MtJava.rgb2Hex(this.get(), this.allowAlpha));
        if (original == CommonComponents.EMPTY) {
            return hex;
        }
        return CommonComponents.joinLines(original, CommonComponents.NEW_LINE, hex);
    }

    public ColorEntry setAllowAlpha(boolean allowAlpha) {
        if (this.getMode() == Mode.PALETTE) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "'{}' is cells, alpha in cells doesn't exists!", this);
            return this;
        }
        this.allowAlpha = allowAlpha;
        return this;
    }

    public ColorEntry setCellSize(int cellSize) {
        if (this.getMode() == Mode.CELLS) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "'{}' is palette, cells in palette doesn't exists!", this);
            return this;
        }
        this.cellSize = cellSize;
        return this;
    }

    public void validate(int color) throws ConfigException {
        if (this.allowAlpha) {
            if (this.invokeAlpha() < 0 || this.invokeAlpha() > 255) {
                throw new ConfigException("Alpha in '" + color + "' is invalid!");
            }
        }
        if (this.invokeRed() < 0 || this.invokeRed() > 255) {
            throw new ConfigException("Red in '" + color + "' is invalid!");
        } else if (this.invokeGreen() < 0 || this.invokeGreen() > 255) {
            throw new ConfigException("Green in '" + color + "' is invalid!");
        } else if (this.invokeBlue() < 0 || this.invokeBlue() > 255) {
            throw new ConfigException("Blue in '" + color + "' is invalid!");
        }
    }

    public int invokeAlpha() {
        if (!this.allowAlpha) {
            throw new ConfigException("Alpha is not allowed in '" + this + "'");
        }
        return FastColor.ARGB32.alpha(this.get());
    }

    public int invokeRed() {
        return FastColor.ARGB32.red(this.get());
    }

    public int invokeGreen() {
        return FastColor.ARGB32.green(this.get());
    }

    public int invokeBlue() {
        return FastColor.ARGB32.blue(this.get());
    }

    public IntList getCells() {
        return this.cells;
    }

    public int getCellSize() {
        return this.cellSize;
    }

    public boolean isAllowAlpha() {
        return this.allowAlpha;
    }

    public Mode getMode() {
        return this.mode;
    }

    public enum Mode {
        PALETTE,
        CELLS
    }
}
