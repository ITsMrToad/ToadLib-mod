package com.mr_toad.lib.mtjava.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class UniqueList<T> extends ArrayList<T> {

    public UniqueList(int initialCapacity) {
        super(initialCapacity);
    }

    public UniqueList() {}

    @SafeVarargs
    public UniqueList(T... elements) {
        super(Arrays.asList(elements));
    }

    public UniqueList(Collection<? extends T> c) {
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

