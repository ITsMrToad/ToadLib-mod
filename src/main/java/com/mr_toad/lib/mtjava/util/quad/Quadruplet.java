package com.mr_toad.lib.mtjava.util.quad;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.mr_toad.lib.mtjava.annotations.TypesAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;

import java.util.function.Function;

@FieldsAreNonnullByDefault
@TypesAreNonnullByDefault
public class Quadruplet <F, S, T, FR> {

    public final F first;
    public final S second;
    public final T third;
    public final FR fourth;

    protected Quadruplet(F first, S second, T third, FR fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public static <F, S, T, FR> Quadruplet<F, S, T, FR> of(final F first, final S second, final T third, final FR fourth) {
        return new Quadruplet<>(first, second, third, fourth);
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

    public FR getFourth() {
        return this.fourth;
    }

    public Quadruplet<S, F, T, FR> swapFS() {
        return Quadruplet.of(this.getSecond(), this.getFirst(), this.getThird(), this.getFourth());
    }

    public Quadruplet<T, S, F, FR> swapFT() {
        return Quadruplet.of(this.getThird(), this.getSecond(), this.getFirst(), this.getFourth());
    }

    public Quadruplet<FR, S, T, F> swapFFR() {
        return Quadruplet.of(this.getFourth(), this.getSecond(), this.getThird(), this.getFirst());
    }

    public Quadruplet<F, FR, T, S> swapSFR() {
        return Quadruplet.of(this.getFirst(), this.getFourth(), this.getThird(), this.getSecond());
    }

    public Quadruplet<F, S, FR, T> swapTFR() {
        return Quadruplet.of(this.getFirst(), this.getSecond(), this.getFourth(), this.getThird());
    }

    public <F2> Quadruplet<F2, S, T, FR> mapFirst(final Function<? super F, ? extends F2> function) {
        return Quadruplet.of(function.apply(this.getFirst()), this.getSecond(), this.getThird(), this.getFourth());
    }

    public <S2> Quadruplet<F, S2, T, FR> mapSecond(final Function<? super S, ? extends S2> function) {
        return Quadruplet.of(this.getFirst(), function.apply(this.getSecond()), this.getThird(), this.getFourth());
    }

    public <T2> Quadruplet<F, S, T2, FR> mapThird(final Function<? super T, ? extends T2> function) {
        return Quadruplet.of(this.getFirst(), this.getSecond(), function.apply(this.getThird()), this.getFourth());
    }

    public <FR2> Quadruplet<F, S, T, FR2> mapFourth(final Function<? super FR, ? extends FR2> function) {
        return Quadruplet.of(this.getFirst(), this.getSecond(), this.getThird(), function.apply(this.getFourth()));
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("First", this.getFirst()).add("Second", this.getSecond()).add("Third", this.getThird()).add("Fourth", this.getFourth()).toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Quadruplet<?, ?, ?, ?> other)) {
            return false;
        }
        return Objects.equal(this.getFirst(), other.getFirst()) && Objects.equal(this.getSecond(), other.getSecond()) && Objects.equal(this.getThird(), other.getThird()) && Objects.equal(this.getFourth(), other.getFourth());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getFirst(), this.getSecond(), this.getThird());
    }
}