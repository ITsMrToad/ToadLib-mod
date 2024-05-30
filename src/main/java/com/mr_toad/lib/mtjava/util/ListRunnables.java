package com.mr_toad.lib.mtjava.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class ListRunnables {

    public final List<Runnable> list = Lists.newArrayList();

    public ListRunnables(ListRunnables list) {
       this(list.list);
    }

    public ListRunnables(Runnable... runnables) {
        Collections.addAll(this.list, runnables);
    }

    public ListRunnables(List<Runnable> list) {
        this.list.addAll(list);
    }

    public ListRunnables(ImmutableList<Runnable> list) {
        this.list.addAll(list);
    }

    public void runAll() {
        this.list.forEach(Runnable::run);
    }

    public void runByIndex(int index) {
        this.list.get(index).run();
    }

}
