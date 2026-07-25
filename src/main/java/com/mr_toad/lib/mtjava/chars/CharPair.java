package com.mr_toad.lib.mtjava.chars;

import com.mojang.datafixers.util.Pair;

///@deprecated This class box primitives.
@Deprecated(since = "1.5.0")
public class CharPair extends Pair<Character, Character> {

    public CharPair(char first, char second) {
        super(first, second);
    }

    public static CharPair of(char first, char second) {
        return new CharPair(first, second);
    }
}
