package com.mr_toad.lib.mtjava.collections.execute;

import java.util.Collection;
import java.util.HashSet;

public class ExecutableHashSet extends HashSet<Runnable> implements Runnable {

    public ExecutableHashSet() {
        super();
    }

    public ExecutableHashSet(int capacity) {
        super(capacity);
    }

    public ExecutableHashSet(Collection<Runnable> c) {
        super(c);
    }

    @Override
    public void run() {
        this.forEach(Runnable::run);
    }

}
