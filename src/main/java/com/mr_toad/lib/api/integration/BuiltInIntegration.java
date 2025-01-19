package com.mr_toad.lib.api.integration;

import java.util.function.BooleanSupplier;

public class BuiltInIntegrations {

    public static BooleanSupplier SPARK_CLOSED = () -> true;

    public static final Integration CATALOGUE = () -> "catalogue";
    public static final Integration QUARK = () -> "quark";
    public static final Integration CREATE = () -> "create";
    public static final Integration SPARK = () -> "spark";

    public static boolean isSparkClosed() {
        return !SPARK.isLoaded() || SPARK_CLOSED.getAsBoolean();
    }

    @ApiStatus.Internal
    public static Optional<ToadConfigScreen> getConfigScreen(String modid, Screen parent) {
        if (!CATALOGUE.isLoaded() || CATALOGUE_CONFIGS.isEmpty() || StringUtils.isEmpty(modid) || !CATALOGUE_CONFIGS.containsKey(modid)) {
            return Optional.empty();
        }
        ToadLib.LOGGER.info(ToadLib.CONFIG, "Loaded ToadConfig for ModMenu of '{}'", modid);
        return Optional.ofNullable(CATALOGUE_CONFIGS.get(modid).apply(parent));
    }

    public static boolean hasToadConfig(String modid) {
        if (!CATALOGUE.isLoaded() || CATALOGUE_CONFIGS.isEmpty()) {
            return false;
        }
        return CATALOGUE_CONFIGS.containsKey(modid);
    }

    @ApiStatus.Internal
    public static<C extends ToadConfig> void setupConfig(String modid, C config) {
        if (!CATALOGUE.isLoaded() || StringUtils.isEmpty(modid) || config == null || CATALOGUE_CONFIGS.containsKey(modid)) {
            return;
        }
        CATALOGUE_CONFIGS.put(modid, parent -> new ToadConfigScreen(parent, config));
    }

    @ApiStatus.Internal
    public static Object2ObjectMap<String, Function<Screen, ToadConfigScreen>> getCatalogueConfigs() {
        return CATALOGUE_CONFIGS;
    }
}
