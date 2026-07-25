package com.mr_toad.lib.mtjava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mr_toad.lib.mtjava.concurrent.Concurrents;
import net.minecraft.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

///@deprecated Methods moved to {@link com.mr_toad.lib.api.util.ToadResourceUtils}
@Deprecated(since = "1.5.0")
public class MTIO {

    public static final ExecutorService IO = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("MTIO-Worker-%s").setUncaughtExceptionHandler(Concurrents::uncaught).build());

    public static void shutdownIO() {
        Concurrents.shutdownService(Util.ioPool().service());
        Concurrents.shutdownService(IO);
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
