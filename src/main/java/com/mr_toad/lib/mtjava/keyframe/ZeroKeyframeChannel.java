package com.mr_toad.lib.mtjava.keyframe;

import java.util.Collections;

public final class ZeroKeyframeChannel extends KeyframeChannel {

    private static final ZeroKeyframeChannel INSTANCE = new ZeroKeyframeChannel();

    private ZeroKeyframeChannel() {
        super(Collections.emptyList());
    }

    @Override
    public void start(long start) {}

    @Override
    public void stop() {}

    @Override
    public void tick() {}

    @Override
    public void add(Keyframe keyframe) {}

    @Override
    public void remove(Keyframe keyframe) {}

    @Override
    public void markDirty() {}

    @Override
    public void advance() {}

    @Override
    public long getCurrentTime() {
        return 0L;
    }

    @Override
    public long getStartTime() {
        return -1L;
    }

    @Override
    public float getValue() {
        return 0.0F;
    }

    @Override
    public float getDuration() {
        return 0.0F;
    }
}
