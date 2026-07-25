package com.mr_toad.lib.mtjava.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;

public class PathOutputStream extends FileOutputStream {

    public PathOutputStream(Path file) throws FileNotFoundException {
        super(file.toFile());
    }

    public PathOutputStream(Path file, boolean append) throws FileNotFoundException {
        super(file.toFile(), append);
    }
}
