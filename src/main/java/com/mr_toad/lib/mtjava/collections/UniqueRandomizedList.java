package com.mr_toad.lib.mtjava.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class UniqueRandomizedList<T> extends RandomizedList<T> {

    public UniqueRandomizedList(int initialCapacity) {
        super(initialCapacity);
    }

    public UniqueRandomizedList() {}

    @SafeVarargs
    public UniqueRandomizedList(T... elements) {
        super(Arrays.asList(elements));
    }

    public UniqueRandomizedList(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public boolean add(T e) {
        if (!this.contains(e)) {
            return super.add(e);
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean added = false;
        for (T e : c) {
            if (this.add(e)) {
                added = true;
            }
        }
        return added;
    }
}

