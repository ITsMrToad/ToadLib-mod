package com.mr_toad.lib.api.client.utils.graphics;

import java.util.Locale;

public class GraphicsException extends IllegalArgumentException {

    public GraphicsException(String msg, In in) {
        super(in.get() + msg);
    }

    public GraphicsException(String msg, In in, Throwable throwable) {
        super(in.get() + msg, throwable);
    }

    public enum In {
        GL,
        MEMORY,
        RENDER,
        INIT;

        public String get() {
            return "[" + this.name().toUpperCase(Locale.ROOT) + "]";
        }
    }
}
