package com.mr_toad.lib.api.config.entry.type;

import org.jetbrains.annotations.NotNull;

public record ConfigEntryType<T>(String name) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ConfigEntryType<?>(String n)) {
            return this.name().equals(n);
        } else {
            return false;
        }
    }

    @Override
    public @NotNull String toString() {
        return this.name();
    }
}
