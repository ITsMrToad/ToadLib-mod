package com.mr_toad.lib.mtjava.io;

import org.jetbrains.annotations.NotNull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class PathInputStream extends FileInputStream {

    public PathInputStream(@NotNull Path path) throws FileNotFoundException {
        super(path.toFile());
    }
}
