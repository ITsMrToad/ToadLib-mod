package com.mr_toad.lib.mtjava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mr_toad.lib.mtjava.concurrent.Concurrents;
import net.minecraft.Util;

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

public class MTIO {

    public static final ExecutorService IO = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("MTIO-Worker-%s").setUncaughtExceptionHandler(MTIO::uncaught).build());

    public static void shutdownIO() {
        Concurrents.shutdownService(Util.ioPool());
        Concurrents.shutdownService(IO);
    }

    public static void clearPackage(Path path) throws IOException {
        MoreFiles.deleteDirectoryContents(path, RecursiveDeleteOption.ALLOW_INSECURE);
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

