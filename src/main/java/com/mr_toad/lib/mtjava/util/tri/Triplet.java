package com.mr_toad.lib.mtjava.util.tri;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.mr_toad.lib.mtjava.annotations.TypesAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;

import java.util.function.Function;
import java.util.stream.Collector;

@FieldsAreNonnullByDefault
@TypesAreNonnullByDefault
public class Triplet<F, S, T> {

    public final F first;
    public final S second;
    public final T third;

    protected Triplet(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <F, S, T> Triplet<F, S, T> of(final F first, final S second, final T third) {
        return new Triplet<>(first, second, third);
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public T getThird() {
        return this.third;
    }

    public Triplet<S, F, T> swapFS() {
        return Triplet.of(this.getSecond(), this.getFirst(), this.getThird());
    }

    public Triplet<T, S, F> swapFT() {
        return Triplet.of(this.getThird(), this.getSecond(), this.getFirst());
    }

    public Triplet<F, T, S> swapST() {
        return Triplet.of(this.getFirst(), this.getThird(), this.getSecond());
    }

    public <F2> Triplet<F2, S, T> mapFirst(final Function<? super F, ? extends F2> function) {
        return Triplet.of(function.apply(this.getFirst()), this.getSecond(), this.getThird());
    }

    public <S2> Triplet<F, S2, T> mapSecond (final Function<? super S, ? extends S2> function) {
        return Triplet.of(this.getFirst(), function.apply(this.getSecond()), this.getThird());
    }

    public <T2> Triplet<F, S, T2> mapThird(final Function<? super T, ? extends T2> function) {
        return Triplet.of(this.getFirst(), this.getSecond(), function.apply(this.getThird()));
    }

    @Beta
    public static <F, S, T> Collector<Triplet<F, S, T>, ?, Table<F, S, T>> toTable() {
        return Tables.toTable(Triplet::getFirst, Triplet::getSecond, Triplet::getThird, HashBasedTable::create);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("First", this.getFirst()).add("Second", this.getSecond()).add("Third", this.getThird()).toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Triplet<?, ?, ?> other)) {
            return false;
        }
        return java.util.Objects.equals(this.getFirst(), other.getFirst()) && java.util.Objects.equals(this.getSecond(), other.getSecond()) && java.util.Objects.equals(this.getThird(), other.getThird());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getFirst(), this.getSecond(), this.getThird());
    }

}
