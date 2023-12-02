package com.mr_toad.lib.api.util;

public record Boolean1N2(boolean has1, boolean has2) {

    public static Boolean1N2 allOn() {
        return new Boolean1N2(true, true);
    }

    public static Boolean1N2 allOff() {
        return new Boolean1N2(false, false);
    }

    public static Boolean1N2 firstOn() {
        return new Boolean1N2(true, false);
    }

    public static Boolean1N2 secondOn() {
        return new Boolean1N2(false, true);
    }

}
