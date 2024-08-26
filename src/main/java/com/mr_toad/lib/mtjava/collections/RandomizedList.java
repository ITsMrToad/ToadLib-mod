package com.mr_toad.lib.mtjava.collections;

import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class RandomizedList<T> extends ArrayList<T> {

    private final Random randomSource = Random.create();

    public RandomizedList(int initialCapacity) {
        super(initialCapacity);
    }

    public RandomizedList() {}

    public RandomizedList(Collection<? extends T> c) {
        super(c);
    }

    public Optional<T> getRandom() {
        if(this.size() > 0) {
            return Optional.of(this.get(this.randomSource.nextInt(this.size())));
        }
        return Optional.empty();
    }

    public Optional<T> removeRandom() {
        if(this.size() > 0) {
            return Optional.of(this.remove(this.randomSource.nextInt(this.size())));
        }
        return Optional.empty();
    }

}
