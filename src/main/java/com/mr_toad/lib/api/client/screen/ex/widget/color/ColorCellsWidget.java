package com.mr_toad.lib.api.client.screen.ex.widget.color;

import com.mr_toad.lib.api.client.utils.ToadClientUtils;
import com.mr_toad.lib.core.ToadLib;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntConsumer;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ColorCellsWidget extends AbstractWidget {

    private final IntList colorsList = new IntArrayList();
    private int titleY = this.getY() - 15;
    private int selectedColor;
    private final IntList colors;
    private final IntConsumer change;
    private final int cellsPerRow;
    private final int cellSize;

    public static ColorCellsWidget.Builder of(IntList colors, IntConsumer change) {
        return new Builder(colors, change);
    }

    public ColorCellsWidget(int x, int y, int w, int h, int cellSize, int defaultValue, Component hoverMsg, IntList colors, IntConsumer change) {
        super(x, y, w, h, hoverMsg);
        this.colors = colors;
        this.change = change;
        this.cellSize = cellSize;

        int nextX = this.getX();
        int i = 0;
        if (!colors.isEmpty()) {
            int nextY = y;
            boolean flag = true;
            for (int j = 0; j < colors.size(); j++) {
                int color = colors.getInt(j);
                if (!this.colorsList.add(color)) {
                    ToadLib.LOGGER.error("Found duplicate entry: '{}'. Removing...", color);
                    colors.removeInt(colors.indexOf(color));
                }

                if (flag) {
                    i++;
                }

                nextX += cellSize + 5;
                if (nextX > w) {
                    nextX = x;
                    nextY += cellSize + 5;
                    flag = false;
                    if (nextY > this.getHeight()) {
                        ToadLib.LOGGER.warn("Color cells list(Cell size: '{}', Cells: '{}') overflow! Re-size...", cellSize, this.size());
                        this.setHeight(h + nextY * 2);
                    }
                }
            }
        }

        this.cellsPerRow = i;
        this.selectedColor = defaultValue;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mx, int my, float pt) {
        int nextX = this.getX();
        int nextY = this.getY();
        for (int i = 0; i < this.colorsList.size(); i++) {
            int color = this.colorsList.getInt(i);
            int borderColor = this.isSelected(color) ? FastColor.ARGB32.color(255, 167, 160, 168) : FastColor.ARGB32.color(255, 128, 120, 120);
            graphics.fill(nextX, nextY, nextX + this.cellSize, nextY + this.cellSize, borderColor);
            graphics.fill(nextX + 2, nextY + 2, nextX + this.cellSize - 2, nextY + this.cellSize - 2, color);

            nextX += this.cellSize + 5;
            if (nextX > this.getWidth()) {
                nextX = this.getX();
                nextY += this.cellSize + 5;
            }
        }

        if (this.getMessage() != CommonComponents.EMPTY) {
            graphics.drawCenteredString(Minecraft.getInstance().font, this.getMessage(), this.getX() * (this.getCellsPerRow() / 2), this.titleY, ToadClientUtils.DEFAULT_TEXT);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (this.colors.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < this.colorsList.size(); i++) {
                int color = this.colorsList.getInt(i);
                int nextX = this.getX() + (i % this.cellsPerRow) * (this.cellSize + 5);
                int nextY = this.getY() + (i / this.cellsPerRow) * (this.cellSize + 5);
                if (mx >= nextX && my >= nextY && mx < nextX + this.cellSize && my < nextY + this.cellSize) {
                    this.onChange(color);
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {}

    public void onChange(int color) {
        this.change.accept(color);
        this.setSelected(color);
    }

    public void setSelected(int color) {
        this.selectedColor = color;
    }

    public void mapTitleY(Int2IntFunction mapper) {
        this.titleY = mapper.apply(this.getY());
    }

    public boolean isSelected(int color) {
        return this.selectedColor == color;
    }

    public int getValue() {
        return this.selectedColor;
    }

    public int getCellsPerRow() {
        return this.cellsPerRow;
    }

    public int size() {
        return this.colorsList.size();
    }

    public static class Builder {

        private int defaultValue;

        private Component title = CommonComponents.EMPTY;

        private int x = 5;
        private int y = 5;
        private int w = 5;
        private int h = 5;

        private int cellSize = 15;

        private final IntList colors;
        private final IntConsumer change;

        public Builder(IntList colors, IntConsumer change) {
            this.colors = colors;
            this.change = change;
            this.defaultValue = colors.getInt(0);
        }

        public Builder size(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            return this;
        }

        public Builder addTitle(Component title) {
            this.title = title;
            return this;
        }

        public Builder setCellSize(int cellSize) {
            this.cellSize = cellSize;
            return this;
        }

        public Builder setDefaultValue(int defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ColorCellsWidget build() {
            return new ColorCellsWidget(this.x, this.y, this.w, this.h, this.cellSize, this.defaultValue, this.title, this.colors, this.change);
        }
    }
}
