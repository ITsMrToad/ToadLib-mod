package com.mr_toad.lib.core;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.api.entity.immune.EffectImmuneStorage;
import com.mr_toad.lib.core.config.ToadLibConfig;
import com.mr_toad.lib.core.resource.ConfigReloadListener;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.saveddata.SavedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.Optional;

public class ToadLib implements ModInitializer {

	public static final Object2ObjectMap<String, Supplier<SavedData>> SAVED_DATA_SUPPLIERS = new Object2ObjectOpenHashMap<>();

	public static final Marker CONFIG = MarkerFactory.getMarker("ToadConfig");
	public static final Logger LOGGER = LoggerFactory.getLogger("ToadLib");

	public static final String MODID = "toadlib";

    public static final Identifier CONFIG_RELOAD_LISTENER = ToadLib.id("config_reloader");
    public static final ToadLibConfig CFG = new ToadLibConfig();

    @Override
	public void onInitialize() {
		EffectImmuneStorage.init();
        ToadConfigs.create(MODID, CFG);
        ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(CONFIG_RELOAD_LISTENER, new ConfigReloadListener());
    }

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}

    @SuppressWarnings("unchecked")
    public static <D extends SavedData> Optional<Supplier<D>> findSavedData(String file) {
		try {
			Supplier<D> supplier = (Supplier<D>) SAVED_DATA_SUPPLIERS.get(file);
			if (supplier == null) {
				ToadLib.LOGGER.error("Saved data with file name '{}', doesn't exists or server level not initialized!", file);
				return Optional.empty();
			} else {
				return Optional.of(Suppliers.memoize(supplier));
			}
		} catch (Exception e) {
			ToadLib.LOGGER.error("Error during get '{}' saved data!", file, e);
			return Optional.empty();
		}
	}

    public static boolean printDebug() {
        return CFG.printDebug.get();
    }
}