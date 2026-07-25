package com.mr_toad.lib.api.util;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.mr_toad.lib.core.ToadLib;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ToadResourceUtils {

    public static final Identifier EMPTY = ToadLib.id("empty");
    public static final Joiner NEW_LINE = Joiner.on("\n");

    public static Path pathOf(String modid, PackType type) {
        return FabricLoader.getInstance().getGameDir().resolve(modid).resolve(type.getDirectory() + "/" + modid);
    }

    public static boolean isURL(String s) {
        return s.trim().contains("://");
    }

    public static ImmutableList<String> readLines(File file) throws IOException {
        return readLines(file.toPath());
    }

    public static ImmutableList<String> readLines(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return readLines(reader);
        }
    }

    public static ImmutableList<String> readLines(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return readLines(reader);
        }
    }

    public static ImmutableList<String> readLines(BufferedReader reader) {
        return ImmutableList.copyOf(reader.lines().iterator());
    }
}
