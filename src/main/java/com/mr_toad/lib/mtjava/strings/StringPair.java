package com.mr_toad.lib.mtjava.strings;

import com.mojang.datafixers.util.Pair;

public final class StringPair extends Pair<String, String> {

    public StringPair(String first, String second) {
        super(first, second);
    }

    public static StringPair of(String first, String second) {
        return new StringPair(first, second);
    }
}