package com.mr_toad.lib.mtjava.chars;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.mr_toad.lib.mtjava.chars.func.Char2CharFunction;
import com.mr_toad.lib.mtjava.chars.func.Char2StringFunction;
import com.mr_toad.lib.mtjava.chars.func.CharSupplier;
import com.mr_toad.lib.mtjava.strings.OptionalString;
import com.mr_toad.lib.mtjava.strings.func.String2CharFunction;
import com.mr_toad.lib.mtjava.strings.func.String2StringFunction;
import com.mr_toad.lib.mtjava.strings.func.StringConsumer;
import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.chars.CharConsumer;
import joptsimple.internal.Strings;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.apache.logging.log4j.util.Chars;

import org.jetbrains.annotations.Nullable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class OptionalChar {

    private static final OptionalChar EMPTY = new OptionalChar();

    private final boolean isPresent;
    private final char value;

    private OptionalChar() {
        this.isPresent = false;
        this.value = Chars.NUL;
    }

    public static OptionalChar empty() {
        return EMPTY;
    }

    private OptionalChar(char value) {
        this.isPresent = true;
        this.value = value;
    }

    public static OptionalChar of(char value) {
        return new OptionalChar(value);
    }

    public char getAsChar() {
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

    public void ifPresent(CharConsumer action) {
        if (this.isPresent) {
            action.accept(this.value);
        }
    }

    public void ifPresentOrElse(CharConsumer action, Runnable emptyAction) {
        if (this.isPresent) {
            action.accept(this.value);
        } else {
            emptyAction.run();
        }
    }

    public char orElse(char other) {
        return this.isPresent ? this.value : other;
    }

    public char orElseGet(CharSupplier supplier) {
        return this.isPresent ? this.value : supplier.getAsChar();
    }

    public char orElseThrow() {
        if (!this.isPresent) {
            throw new NoSuchElementException("No value present");
        }
        return this.value;
    }

    public <X extends Throwable> char orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isPresent) {
            return this.value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public Stream<Character> stream() {
        if (this.isPresent()) {
            return Stream.of(this.value);
        } else {
            return Stream.empty();
        }
    }

    public Stream<String> mapToString(Char2StringFunction mapper) {
        Preconditions.checkNotNull(mapper);
        String s = this.map(mapper).orElse(null);
        return Stream.ofNullable(s);
    }

    public OptionalChar map(Char2CharFunction mapper) {
        Preconditions.checkNotNull(mapper);
        if (!this.isPresent()) {
            return empty();
        } else {
            return OptionalChar.of(mapper.apply(this.value));
        }
    }

    public OptionalString map(Char2StringFunction mapper) {
        Preconditions.checkNotNull(mapper);
        if (!isPresent()) {
            return OptionalString.empty();
        } else {
            return OptionalString.of(mapper.applyAsString(this.value));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof OptionalChar other && (this.isPresent && other.isPresent ? Objects.equal(this.value, other.value) : this.isPresent == other.isPresent);
    }

    @Override
    public int hashCode() {
        return this.isPresent ? Character.hashCode(this.value) : 0;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add(this.isPresent ? String.format("OptionalChar[%s]", this.value) : "Empty", this.value).toString();
    }
}
