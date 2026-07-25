package com.mr_toad.lib.mtjava.strings.func;

@FunctionalInterface
public interface StringPredicate {

    boolean test(String s);

    static boolean startOrEndsWith(String s, String path) {
        return s.startsWith(path) || s.endsWith(path);
    }
}
