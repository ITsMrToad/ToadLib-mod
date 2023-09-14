package com.mr_toad.lib.api.outer;

import com.mr_toad.lib.core.ToadLib;

import java.util.*;
import java.util.function.Predicate;

public class ItemStackCollect {

    @SafeVarargs
    public static <T> Collection<T> collectAll(Set<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                ToadLib.LOGGER.warn("Item {} not found", item);
            }
        }

        return ret;
    }

    public static <T> Collection<T> collectAll(Set<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }

        return ret;
    }

}
