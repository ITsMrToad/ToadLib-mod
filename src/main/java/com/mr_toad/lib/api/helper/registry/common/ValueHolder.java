package com.mr_toad.lib.api.helper.registry.common;

import com.mr_toad.lib.core.ToadLib;
import net.minecraft.core.Holder;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public record ValueHolder<O>(O obj) implements Supplier<O>, Comparable<O>, Cloneable {

    public Holder<O> direct() {
        return Holder.direct(this.get());
    }

    public Stream<O> stream() {
        return Stream.of(this.get());
    }

    @Override
    public O get() {
        return this.obj();
    }

    @Override
    public int compareTo(@NotNull O o) {
        if (this.get() instanceof @SuppressWarnings("rawtypes") Comparable c) {
            //noinspection unchecked
            return c.compareTo(o);
        } else {
            ToadLib.LOGGER.error("'{}' not implements 'Comparable'!", this.get());
            return -1;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else if (obj instanceof ValueHolder<?> h) {
            return Objects.equals(this.get(), h.get());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.get().hashCode();
    }

    @Override
    public String toString() {
        return "VH[" + this.get() + "]";
    }
}
