package com.mr_toad.lib.mtjava.util;

import com.mojang.datafixers.util.Pair;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalPair<F, S> {

    private static final OptionalPair<?, ?> EMPTY = new OptionalPair<>();

    public final Pair<F, S> content;
    private OptionalPair() {
        this(Pair.of(null, null));
    }

    private OptionalPair(Pair<F, S> pair) {
        this.content = pair;
    }

    @SuppressWarnings("unchecked")
    public static<F, S> OptionalPair<F, S> empty() {
        return (OptionalPair<F, S>) EMPTY;
    }

    public static<F, S> OptionalPair<F, S> of(Pair<F, S> value) {
        return new OptionalPair<>(Objects.requireNonNull(value));
    }

    public static <F, S> OptionalPair<F, S> ofNullable(@Nullable Pair<F, S> value) {
        return value == null ? OptionalPair.empty() : new OptionalPair<>(value);
    }

    public F getFirst() {
        if (this.content.getFirst() == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.content.getFirst();
    }

    public S getSecond() {
        if (this.content.getSecond() == null) {
            throw new NoSuchElementException("No value present");
        }
        return this.content.getSecond();
    }

    public<F2> Pair<F2, S> mapFirst(Function<? super F, ? extends F2> func) {
        return Pair.of(func.apply(this.getFirst()), this.getSecond());
    }

    public<S2> Pair<F, S2> mapSecond(Function<? super S, ? extends S2> func) {
        return Pair.of(this.getFirst(), func.apply(this.getSecond()));
    }

    public<F2, S2> Pair<F2, S2> mapAll(Function<? super F, ? extends F2> func1, Function<? super S, ? extends S2> func2) {
        return Pair.of(func1.apply(this.getFirst()), func2.apply(this.getSecond()));
    }

    public boolean firstIsPresent() {
        return this.content.getFirst() != null;
    }

    public boolean secondIsPresent() {
        return this.content.getSecond() != null;
    }

    public boolean firstIsEmpty() {
        return this.content.getFirst() == null;
    }

    public boolean secondIsEmpty() {
        return this.content.getSecond() == null;
    }

    public boolean allIsPresent() {
        return this.firstIsPresent() && this.secondIsPresent();
    }

    public boolean allIsEmpty() {
        return this.firstIsEmpty() && this.secondIsEmpty();
    }

    public void firstIfPresent(Consumer<? super F> action) {
        if (this.content.getFirst() != null) {
            action.accept(this.content.getFirst());
        }
    }

    public void secondIfPresent(Consumer<? super S> action) {
        if (this.content.getSecond() != null) {
            action.accept(this.content.getSecond());
        }
    }

    public void allIfPresent(BiConsumer<? super F, ? super S> action) {
        if (this.content != null) {
            action.accept(this.getFirst(), this.getSecond());
        }
    }

    public void ifPresentOrElse(BiConsumer<? super F, ? super S> action, Runnable emptyAction) {
        if (this.content != null) {
            action.accept(this.getFirst(), this.getSecond());
        } else {
            emptyAction.run();
        }
    }

    public OptionalPair<F, S> filter(BiPredicate<? super F, ? super S> predicate) {
        Objects.requireNonNull(predicate);
        if (!this.allIsEmpty()) {
            return this;
        } else {
            return predicate.test(this.getFirst(), this.getSecond()) ? this : OptionalPair.empty();
        }
    }

    public Stream<F> streamFirst() {
        if (this.firstIsEmpty()) {
            return Stream.empty();
        } else {
            return Stream.of(this.getFirst());
        }
    }

    public Stream<S> streamSecond() {
        if (this.secondIsEmpty()) {
            return Stream.empty();
        } else {
            return Stream.of(this.getSecond());
        }
    }

    @SuppressWarnings("unchecked")
    public OptionalPair<F, S> or(Supplier<? extends OptionalPair<? extends F, ? extends S>> supplier) {
        Objects.requireNonNull(supplier);
        if (this.allIsPresent()) {
            return this;
        } else {
            OptionalPair<F, S> or = (OptionalPair<F, S>) supplier.get();
            return Objects.requireNonNull(or);
        }
    }

    public OptionalPair<S, F> inverse() {
        return OptionalPair.of(Pair.of(this.getSecond(), this.getFirst()));
    }

    public F firstOrElse(F other) {
        return this.getFirst() != null ? this.getFirst() : other;
    }

    public S secondOrElse(S other) {
        return this.getSecond() != null ? this.getSecond() : other;
    }

    public F firstOrElseGet(Supplier<? extends F> supplier) {
        return this.getFirst() != null ? this.getFirst() : supplier.get();
    }

    public S secondOrElseGet(Supplier<? extends S> supplier) {
        return this.getSecond() != null ? this.getSecond() : supplier.get();
    }

    public <X extends Throwable> Pair<F, S> orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.content != null) {
            return this.content;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public Collector<Pair<F, S>, ?, Map<F, S>> toMap() {
        if (this.allIsEmpty()) {
            throw new NoSuchElementException("No value present");
        } else {
            return Collectors.toMap(Pair::getFirst, Pair::getSecond);
        }
    }

    @Override
    public int hashCode() {
        return this.content.hashCode();
    }

    @Override
    public String toString() {
        String s = String.format("OptionalPairFirst[%s]", this.getFirst());
        String s2 = String.format("OptionalPairSecond[%s]", this.getSecond());

        StringBuilder builder =  new StringBuilder("OptionalPair<");

        if (this.firstIsPresent()) {
            builder.append(s);
        }

        if (this.secondIsPresent()) {
            if (this.firstIsPresent()) {
                builder.append(", ");
            }
            builder.append(s2);
        }

        if (this.allIsEmpty()) {
            builder.delete(builder.length(), builder.length());
            builder.append("<EmptyOptionalPair>");
        } else {
            builder.append(">");
        }

        return builder.toString();
    }

}
