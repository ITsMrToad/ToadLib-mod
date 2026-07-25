package com.mr_toad.lib.api.client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.network.chat.Component;

public class StringWidgetBuilder {

    private Font font = Minecraft.getInstance().font;
    private int x = 0;
    private int y = 0;
    private int w = 0;
    private int h = this.font.lineHeight;

    private final Component text;

    public StringWidgetBuilder(String text) {
        this(Component.literal(text));
    }

    public StringWidgetBuilder(Component text) {
        this.text = text;
    }

    public StringWidgetBuilder center(int line) {
        this.x = line / 2;
        this.w = line;
        return this;
    }

    public StringWidgetBuilder x(int x) {
        this.x = x;
        return this;
    }

    public StringWidgetBuilder y(int y) {
        this.y = y;
        return this;
    }

    public StringWidgetBuilder w(int w) {
        this.w = w;
        return this;
    }

    public StringWidgetBuilder h(int h) {
        this.h = h;
        return this;
    }

    public StringWidgetBuilder font(Font font) {
        this.font = font;
        return this;
    }

    public StringWidget build() {
        return new StringWidget(this.x, this.y, this.w, this.h, this.text, this.font);
    }
}
