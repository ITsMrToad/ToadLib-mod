package com.mr_toad.lib.api.integration;

import java.util.function.BooleanSupplier;

public class BuiltInIntegrations {

    public static BooleanSupplier SPARK_CLOSED = () -> true;

    public static final Integration QUARK = () -> "quark";
    public static final Integration CREATE = () -> "create";
    public static final Integration SPARK = () -> "spark";

    public static boolean isSparkClosed() {
        return !SPARK.isLoaded() || SPARK_CLOSED.getAsBoolean();
    }
}
