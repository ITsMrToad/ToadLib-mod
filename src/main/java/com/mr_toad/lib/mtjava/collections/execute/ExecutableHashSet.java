package com.mr_toad.lib.mtjava.collections.execute;

import com.mr_toad.lib.mtjava.collections.PolledArrayDeque;

import java.util.HashSet;

public class ExecutableHashSet extends HashSet<Runnable> implements Runnable {

    @Override
    public void run() {
        this.forEach(Runnable::run);
    }

    public void sequence() {
        PolledArrayDeque<Runnable> deque = new PolledArrayDeque<>(this);
        deque.pollAll(Runnable::run);
    }
}
