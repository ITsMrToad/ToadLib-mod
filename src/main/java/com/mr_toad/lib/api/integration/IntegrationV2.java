package com.mr_toad.lib.api.integration;

import com.mr_toad.lib.api.config.ToadConfig;
import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.lib.api.util.ToadResourceUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

import java.nio.file.Path;
import java.util.Optional;

public class IntegrationV2 {

    public static final IntegrationV2 MOD_MENU = new IntegrationV2("modmenu");

    private Path assets = null;
    private Path data = null;

    private final String modid;
    private final boolean loaded;

    public IntegrationV2(String modid) {
        this.modid = modid;
        this.loaded = FabricLoader.getInstance().isModLoaded(modid);
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public Optional<Path> getAssets() {
        if (!this.loaded) {
            return Optional.empty();
        }
        if (this.assets == null) {
            this.assets = ToadResourceUtils.pathOf(this.modid, PackType.CLIENT_RESOURCES);
        }
        return Optional.of(this.assets);
    }

    public Optional<Path> getData() {
        if (!this.loaded) {
            return Optional.empty();
        }
        if (this.data == null) {
            this.data = ToadResourceUtils.pathOf(this.modid, PackType.SERVER_DATA);
        }
        return Optional.of(this.data);
    }

    public Identifier findLocation(String s) {
        if (!this.loaded) {
            return ToadResourceUtils.EMPTY;
        }
        return Identifier.fromNamespaceAndPath(this.modid, s);
    }

    public Optional<ToadConfig> tryGetToadConfig() {
        if (!this.loaded) {
            return Optional.empty();
        }
        return ToadConfigs.byId(this.modid);
    }
}
