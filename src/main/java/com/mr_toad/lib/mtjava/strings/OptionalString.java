package com.mr_toad.lib.mtjava.strings;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.mr_toad.lib.mtjava.chars.OptionalChar;
import com.mr_toad.lib.mtjava.strings.func.String2CharFunction;
import com.mr_toad.lib.mtjava.strings.func.String2StringFunction;
import com.mr_toad.lib.mtjava.strings.func.StringConsumer;
import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import joptsimple.internal.Strings;
import org.apache.logging.log4j.util.Chars;

import org.jetbrains.annotations.Nullable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class OptionalString {

    private static final OptionalString EMPTY = new OptionalString();

    private final boolean isPresent;
    private final String value;

    private OptionalString() {
        this.isPresent = false;
        this.value = Strings.EMPTY;
    }

    public static OptionalString empty() {
        return EMPTY;
    }

    private OptionalString(String value) {
        this.isPresent = true;
        this.value = value;
    }

    public static OptionalString of(String value) {
        return new OptionalString(value);
    }

    public static OptionalString ofNullable(@Nullable String value) {
        return value == null ? empty() : of(value);
    }

    public String getAsString() {
        if (!this.isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public boolean isEmpty() {
        return !this.isPresent;
    }

    public void ifPresent(StringConsumer action) {
        if (this.isPresent) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(StringConsumer action, Runnable emptyAction) {
        if (this.isPresent) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public String orElse(String other) {
        return this.isPresent ? this.value : other;
    }

    public String orElseGet(StringSupplier supplier) {
        return this.isPresent ? this.value : supplier.getAsString();
    }

    public String orElseThrow() {
        if (!this.isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public <X extends Throwable> String orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isPresent) {
            return this.value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public Stream<String> stream() {
        if (this.isPresent) {
            return Stream.of(this.value);
        } else {
            return Stream.empty();
        }
    }

    public Stream<Character> mapToChar(String2CharFunction mapper) {
        Preconditions.checkNotNull(mapper);
        char s = this.map(mapper).orElse(Chars.NUL);
        return Stream.ofNullable(s);
    }

    public OptionalString map(String2StringFunction mapper) {
        Preconditions.checkNotNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return OptionalString.of(mapper.applyAsString(this.value));
        }
    }

    public OptionalChar map(String2CharFunction mapper) {
        Preconditions.checkNotNull(mapper);
        if (!isPresent()) {
            return OptionalChar.empty();
        } else {
            return OptionalChar.of(mapper.applyAsChar(this.value));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof OptionalString other && (this.isPresent && other.isPresent ? Objects.equal(this.value, other.value) : this.isPresent == other.isPresent);
    }

    @Override
    public int hashCode() {
        return this.isPresent ? this.value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add(this.isPresent ? String.format("OptionalString[%s]", this.value) : "Empty", this.value).toString();
    }
}
