package com.mr_toad.lib.mtjava.keyframe;

import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;

public record Keyframe(float value, long timestamp, Interpolation interpolation) {}
