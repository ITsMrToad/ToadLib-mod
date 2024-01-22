package com.mr_toad.lib.api.outer;

import com.google.common.collect.Lists;
import com.mr_toad.lib.core.ToadLib;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class ItemStackCollect {

    @SafeVarargs
    public static <I> Collection<I> collectAll(Set<? extends I> src, I... items) {
        List<I> ret = Arrays.asList(items);
        for (I item : items) {
            if (!src.contains(item)) {
                ToadLib.LOGGER.warn("[Collect] Item: {} not found", item);
            }
        }

        return ret;
    }

    public static <I> Collection<I> collectAll(Set<I> set, Predicate<I> predicate) {
        List<I> ret = Lists.newArrayList();

        Iterator<I> iterator = set.iterator();
        while (iterator.hasNext()) {
            I item = iterator.next();
            if (predicate.test(item)) {
                iterator.remove();
                ret.add(item);
            }
        }

        return ret;
    }

}

