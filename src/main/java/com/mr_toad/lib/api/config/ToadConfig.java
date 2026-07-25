package com.mr_toad.lib.api.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.api.config.error.ConfigException;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

public class ToadConfig {

    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected static final String NO_OWNER = "noownermodid";

    protected final ObjectList<ConfigEntry<?, ?>> entries = new ObjectArrayList<>();
    protected final String path;

    private final String modid;

    protected ToadConfig(StringSupplier path, String modid) {
        this.path = path.getAsString();
        this.modid = modid;
    }

    ///@deprecated Use other constructor, this constructor not initialize modid
    @Deprecated(since = "idk")
    protected ToadConfig(StringSupplier path) {
        this(path, NO_OWNER);
    }

    public void load() {
        if (Files.notExists(this.getConfig())) {
            this.save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(this.getConfig(), StandardCharsets.UTF_8)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            this.entries.forEach(configEntry -> {
                if (root.has(configEntry.toString())) {
                    JsonElement element = root.get(configEntry.toString());
                    configEntry.load(element);
                }
            });
        } catch (IOException e) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to load '{}'", this.path, e);
        }
    }

    public void save() {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.getConfig().toFile()), StandardCharsets.UTF_8)) {
            JsonObject object = new JsonObject();
            this.entries.forEach(configEntry -> configEntry.save(object));
            GSON.toJson(object, writer);
        } catch (IOException e) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to save '{}'", this.path, e);
        }
    }

    public <T, E extends ConfigEntry<T, E>> E register(E instance) {
        this.entries.add(instance);
        return instance;
    }

    public ImmutableList<@NotNull ConfigEntry<?, ?>> getEntries() {
        return ImmutableList.copyOf(this.entries);
    }

    public Optional<ConfigEntry<?, ?>> getEntryByID(int index) {
        return Optional.of(this.getEntries().get(index));
    }

    public OptionalInt indexOf(ConfigEntry<?, ?> entry) {
        int i = this.getEntries().indexOf(entry);
        return i == -1 ? OptionalInt.empty() : OptionalInt.of(i);
    }

    public Component title() {
        return Component.literal(this.getConfig().toString());
    }

    public Path getConfig() {
        return FabricLoader.getInstance().getConfigDir().resolve(this.path + ".json");
    }

    public boolean shouldCreateScreen() {
        return true;
    }

    public String getOwner() {
        if (Objects.equals(this.modid, NO_OWNER)) {
            throw new ConfigException("ModID of '" + this.modid + "' must be non null!");
        }
        return this.modid;
    }
}
