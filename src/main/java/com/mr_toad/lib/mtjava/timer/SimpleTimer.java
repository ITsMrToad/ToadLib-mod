package com.mr_toad.lib.mtjava.timer;

public class SimpleTimer {

    private boolean running = false;
    private final long duration;
    private long time = 0L;

    public SimpleTimer(long duration) {
        this.duration = duration;
    }

    public void startIfStopped() {
        if (!this.running) {
            this.start();
        }
    }

    public void stopIfStarted() {
        if (this.running) {
            this.stop();
        }
    }

    public void start() {
        this.setRunning(true);
    }

    public final void tick() {
        this.tick(1);
    }

    public void tick(long step) {
        if (this.running) {
            this.activeTick(step);
        } else {
            this.inactiveTick(step);
        }
    }

    protected void activeTick(long step) {
        this.time += step;
        if (this.time >= this.duration) {
            this.stop();
        }
    }

    protected void inactiveTick(long step) {}

    public void stop() {
        this.setRunning(false);
        this.time = 0L;
    }

    public void toggle() {
        if (this.running) {
            this.stop();
        } else {
            this.start();
        }
    }

    public void setRunning(boolean running) {
        this.onToggle(running);
        this.running = running;
    }

    public void onToggle(boolean running) {}

    public long getDuration() {
        return this.duration;
    }

    public long getRemainingTime() {
        return this.duration - this.time;
    }

    public long getTime() {
        return this.time;
    }

    public boolean isRunning() {
        return this.running;
    }
}
