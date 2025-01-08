package com.mr_toad.lib.api.config.entry.type;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ConfigEntryType(String name) {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ConfigEntryType type) {
            return this.name().equals(type.name());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name();
    }
}
