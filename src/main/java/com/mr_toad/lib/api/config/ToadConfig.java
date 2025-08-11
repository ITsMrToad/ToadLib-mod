package com.mr_toad.lib.api.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@OnlyIn(Dist.CLIENT)
public class ToadConfig {

    protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    protected final ObjectList<ConfigEntry<?, ?>> entries = new ObjectArrayList<>();
    protected final String path;

    protected ToadConfig(StringSupplier path) {
        this.path = path.getAsString();
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
            ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to save '{}'", this.path);
        }
    }


    public <T, E extends ConfigEntry<T, E>> E register(E instance) {
        this.entries.add(instance);
        return instance;
    }

    public ImmutableList<ConfigEntry<?, ?>> getEntries() {
        return ImmutableList.copyOf(this.entries);
    }

    public Component title() {
        return CommonComponents.EMPTY;
    }

    public Path getConfig() {
        return FMLPaths.CONFIGDIR.get().resolve(this.path + ".toadcfg");
    }

    public boolean shouldCreateScreen() {
        return true;
    }
}

