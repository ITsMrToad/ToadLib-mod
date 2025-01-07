package com.mr_toad.lib.mtjava.collections;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.function.Consumer;

public class SimpleArrayDeque<E> extends ArrayDeque<E> {

    public SimpleArrayDeque() {
        super();
    }

    public SimpleArrayDeque(int numElements) {
        super(numElements);
    }

    public SimpleArrayDeque(Collection<? extends E> c) {
        super(c);
    }

    public void popPush(E e, Consumer<E> action) {
        this.push(e);
        action.accept(e);
        this.pop();
    }

    public void pollAll(Consumer<E> action) {
        while (this.iterator().hasNext()) {
            action.accept(this.pollFirst());
        }
    }
}
