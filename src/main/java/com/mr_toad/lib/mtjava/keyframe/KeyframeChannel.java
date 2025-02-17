package com.mr_toad.lib.mtjava.keyframe;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.math.interpolation.Interpolation;
import com.mr_toad.lib.mtjava.math.interpolation.InterpolationContext;
import it.unimi.dsi.fastutil.floats.Float2LongMap;
import net.minecraft.util.Mth;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Beta
public class KeyframeChannel {

    private final List<Keyframe> keyframes = Lists.newArrayList();

    private boolean dirty = false;
    private long startTick = -1;
    private long currentTick = 0L;

    public KeyframeChannel(Float2LongMap keyframes, Interpolation interpolation) {
        if (!keyframes.isEmpty()) {
            keyframes.forEach((value, timestamp) -> this.keyframes.add(new Keyframe(value, timestamp, interpolation)));
            this.markDirty();
        }
    }

    public KeyframeChannel(Keyframe... keyframes) {
        if (keyframes.length != 0) {
            Collections.addAll(this.keyframes, keyframes);
            this.markDirty();
        }
    }

    public KeyframeChannel(Collection<Keyframe> keyframes) {
        if (!keyframes.isEmpty()) {
            this.keyframes.addAll(keyframes);
            this.markDirty();
        }
    }

    public float getValue() {
        if (this.getKeyframes().isEmpty()) {
            return 0.0F;
        } else if (!this.isRunning()) {
            ToadLib.LOGGER.error("Channel not started!");
            return 0.0F;
        } else {
            if (this.dirty) {
                this.advance();
            }

            int size = this.getKeyframes().size();
            if (this.getKeyframes().size() == 1 || this.currentTick <= this.getKeyframes().get(0).timestamp()) {
                return this.getKeyframes().get(0).value();
            } else if (this.currentTick >= this.getKeyframes().get(size - 1).timestamp()) {
                return this.getKeyframes().get(size - 1).value();
            } else {
                int index = Mth.binarySearch(0, size, i -> this.getKeyframes().get(i).timestamp() > this.currentTick);
                int prevIndex = index - 1;

                Keyframe previous = this.getKeyframes().get(prevIndex);
                Keyframe next = this.getKeyframes().get(index);

                float lastValue = (prevIndex > 0) ? this.getKeyframes().get(prevIndex - 1).value() : previous.value();
                float currentValue = previous.value();
                float nextValue = next.value();
                float postNextValue = (index + 1 < size) ? this.getKeyframes().get(index + 1).value() : next.value();

                float segmentDuration = next.timestamp() - previous.timestamp();
                float x = (this.currentTick - previous.timestamp()) / segmentDuration;

                return previous.interpolation().interpolate(new InterpolationContext(lastValue, currentValue, nextValue, postNextValue, x));
            }
        }
    }

    public void start(long start) {
        if (start < 0) {
            ToadLib.LOGGER.error("Start time is negative!");
            return;
        }
        this.startTick = start;
    }

    public void stop() {
        this.startTick = -1;
        this.currentTick = 0;
    }

    public void tick() {
        if (this.isRunning()) {
            this.currentTick++;
            if (this.dirty) {
                this.advance();
            }
        }
    }

    public void add(Keyframe keyframe) {
        Preconditions.checkNotNull(keyframe, "Keyframe cannot be null!");
        if (this.isRunning() && keyframe.timestamp() <= this.currentTick) {
            ToadLib.LOGGER.error("Keyframe cannot be added if the channel is launched!");
            return;
        }

        this.keyframes.add(keyframe);
        this.markDirty();
    }

    public void remove(Keyframe keyframe) {
        Preconditions.checkNotNull(keyframe, "Keyframe cannot be null!");
        if (this.isRunning() && keyframe.timestamp() <= this.currentTick) {
            ToadLib.LOGGER.error("Keyframe cannot be removed if the channel is launched!");
            return;
        }
        this.keyframes.remove(keyframe);
        this.markDirty();
    }

    public void markDirty() {
        if (!this.dirty) {
            this.dirty = true;
        }
    }

    public void advance() {
        this.keyframes.sort(Comparator.comparingLong(Keyframe::timestamp));
        this.dirty = false;
    }

    public boolean isRunning() {
        return this.startTick != -1;
    }

    public float getDuration() {
        if (this.getKeyframes().size() == 1) {
            return 0L;
        } else {
            return (this.getKeyframes().get(this.getKeyframes().size() - 1).timestamp() - (this.isRunning() ? this.startTick : this.getKeyframes().get(0).timestamp())) / 20F;
        }
    }

    public long getCurrentTime() {
        return this.currentTick;
    }

    public long getStartTime() {
        return this.startTick;
    }

    public ImmutableList<Keyframe> getKeyframes() {
        return ImmutableList.copyOf(this.keyframes);
    }
}
