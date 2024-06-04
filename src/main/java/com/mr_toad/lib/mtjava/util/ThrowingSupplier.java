package com.mr_toad.lib.mtjava.util;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public record ThrowingSupplier<E>(E obj) implements Supplier<E> {

    public static <E, T extends Throwable> E setupThrowing(E o, @Nullable T t) throws T {
        ThrowingSupplier<E> supplier = new ThrowingSupplier<>(o);
        if (t != null) {
            throw t;
        }
        return supplier.get();
    }

    @Override
    public E get() {
        return this.obj;
    }

    @Override
    public String toString() {
        return "<ThrowingSupplier[" + this.obj.toString() + "]>";
    }

    @Override
    public int hashCode() {
        return this.obj.hashCode();
    }
}
