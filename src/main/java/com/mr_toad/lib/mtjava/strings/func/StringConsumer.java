package com.mr_toad.lib.mtjava.strings.func;

import com.google.common.base.Preconditions;

@FunctionalInterface
public interface StringConsumer {

    void accept(String s);

    default StringConsumer andThen(StringConsumer after) {
        Preconditions.checkNotNull(after);
        return s -> {
            this.accept(s);
            after.accept(s);
        };
    }

}
