package com.mr_toad.lib.mtjava.keyframe;

import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import org.jspecify.annotations.NonNull;

public record Keyframe(float value, long timestamp, Interpolation interpolation) {

    @Override
    public @NonNull String toString() {
        return this.value() + ":" + this.timestamp();
    }
}
