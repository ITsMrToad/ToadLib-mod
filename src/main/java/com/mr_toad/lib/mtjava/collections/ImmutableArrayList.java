package com.mr_toad.lib.mtjava.collections;

import com.google.common.collect.Iterators;
import org.apache.commons.lang3.ArrayUtils;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableArrayList<T> implements List<T> {

    private final T[] array;

    @SuppressWarnings("unchecked")
    public ImmutableArrayList(Collection<T> list) {
        this(list.toArray((T[]) new Object[0]));
    }

    public ImmutableArrayList(T[] array) {
        this.array = array;
    }

    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    public boolean isEmpty() {
        return this.array.length == 0;
    }

    @Override
    public boolean contains(Object o) {
        return ArrayUtils.contains(this.array, o);
    }

    @Override
    public @NotNull Iterator<T> iterator() {
        return Iterators.forArray(this.array);
    }

    @Override
    public Object @NotNull [] toArray() {
        return this.array.clone();
    }

    @Override
    @SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
    public <T1> T1 @NotNull [] toArray(T1[] dst) {
        T[] src = this.array;

        if (dst.length < src.length) {
            return (T1[]) Arrays.copyOf(src, src.length, dst.getClass());
        }

        System.arraycopy(src, 0, dst, 0, src.length);

        if (dst.length > src.length) {
            dst[src.length] = null;
        }

        return dst;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!ArrayUtils.contains(this.array, o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        return this.array[index];
    }

    @Override
    public int indexOf(Object o) {
        return ArrayUtils.indexOf(this.array, o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return ArrayUtils.lastIndexOf(this.array, o);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public @NotNull ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public @NotNull ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("List is immutable!");
    }

    @Override
    public @NotNull List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("List is immutable!");
    }
}
