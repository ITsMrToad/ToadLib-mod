package com.mr_toad.lib.api.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.mr_toad.lib.api.config.entry.ConfigEntry;
import com.mr_toad.lib.core.ToadLib;
import com.mr_toad.lib.mtjava.collections.UniqueList;
import com.mr_toad.lib.mtjava.io.MTIO;
import com.mr_toad.lib.mtjava.strings.OptionalString;
import com.mr_toad.lib.mtjava.strings.func.StringFunction;
import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

@OnlyIn(Dist.CLIENT)
public class ToadConfig {

    protected static final Splitter OPTION_SPLITTER = Splitter.on(':').limit(2);

    protected final UniqueList<ConfigEntry<?, ?>> entries;
    protected final String path;

    protected ToadConfig(StringSupplier path) {
        this.path = path.getAsString();
        this.entries = new UniqueList<>();
    }

    public void load() {
        if (Files.notExists(this.getConfig())) {
            this.save();
            return;
        }

        CompoundTag nbt = new CompoundTag();

        try {
            MTIO.readLines(this.getConfig()).forEach(line -> {
                Iterator<String> iterator = OPTION_SPLITTER.split(line).iterator();
                nbt.putString(iterator.next(), iterator.next());
            });
        } catch (IOException e) {
            ToadLib.LOGGER.error(ToadLib.CONFIG, "Failed to load '{}'", this.path, e);
        }

        StringFunction<OptionalString> value = s -> nbt.contains(s) ? OptionalString.of(nbt.getString(s)) : OptionalString.empty();
        this.entries.forEach(configEntry -> value.apply(configEntry.toString()).ifPresent(s -> {
            JsonReader reader = new JsonReader(new StringReader(s.isEmpty() ? "\"\"" : s));
            JsonElement element = JsonParser.parseReader(reader);
            configEntry.load(element);
        }));
    }

    public void save() {
        try (final PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.getConfig().toFile()), StandardCharsets.UTF_8))) {
            this.entries.forEach(configEntry -> {
                ToadLib.LOGGER.info(ToadLib.CONFIG, "Saved '{}', '{}'", configEntry, configEntry.get());
                configEntry.save(writer);
            });
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
        return FMLPaths.CONFIGDIR.get().resolve(this.path + ".txt");
    }

    public boolean shouldCreateScreen() {
        return true;
    }
}
