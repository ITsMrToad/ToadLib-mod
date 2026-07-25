package com.mr_toad.lib.api.config.util;

public enum PageSeparateLine {

    EQUALS('=', false),
    CIRCLE('●', true),
    EMPTY_CIRCLE('○', true),
    LINE('-', false);

    private final char separator;
    public final boolean requireSpace;

    PageSeparateLine(char separator, boolean requireSpace) {
        this.separator = separator;
        this.requireSpace = requireSpace;
    }

    public String getFormatted() {
        int fullLength = this.requireSpace ? 16 : 32;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fullLength; i++) {
            builder.append(this.separator);
            if (this.requireSpace && builder.length() < 32) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }
}
