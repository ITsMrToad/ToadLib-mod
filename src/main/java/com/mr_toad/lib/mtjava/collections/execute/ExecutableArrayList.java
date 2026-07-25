package com.mr_toad.lib.mtjava.collections.execute;

import java.util.ArrayList;
import java.util.Collection;

public class ExecutableArrayList extends ArrayList<Runnable> implements Runnable {

    public ExecutableArrayList() {
        super();
    }

    public ExecutableArrayList(int capacity) {
        super(capacity);
    }

    public ExecutableArrayList(Collection<Runnable> c) {
        super(c);
    }

    @Override
    public void run() {
        if (!this.isEmpty()) {
            this.forEach(Runnable::run);
        }
    }
}
