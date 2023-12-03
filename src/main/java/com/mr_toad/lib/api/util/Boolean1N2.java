package com.mr_toad.lib.api.util;

public record Boolean1N2(boolean first, boolean second) {

    public static final Boolean1N2 ALL = new Boolean1N2(true, true);
    public static final Boolean1N2 FIRST = new Boolean1N2(true, false);
    public static final Boolean1N2 SECOND = new Boolean1N2(false, true);
    public static final Boolean1N2 NULL = new Boolean1N2(false, false);

}
