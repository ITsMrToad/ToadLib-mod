package com.mr_toad.lib.mtjava.timer;

import com.google.common.annotations.Beta;

@Beta //Not tested
public class SystemTimer {

    public long start = -1;
    public long end = 0L;

    public final void start() {
        this.start(System.currentTimeMillis());
    }

    public void start(long time) {
        this.start = time;
    }

    public void stop() {
        this.assertStarted();
        if (this.end == 0L) {
            this.end = System.currentTimeMillis();
        }
    }

    public long getElapsedTime() {
        if (this.end == 0L) {
            this.stop();
        }
        return Math.abs(this.end - this.start);
    }

    public void assertStarted() {
        if (!this.isStarted()) {
            throw new IllegalStateException("Timer not started!");
        }
    }

    public boolean isStarted() {
        return this.start != -1;
    }
}
