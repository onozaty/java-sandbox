package com.github.onozaty.java8to17;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

public class TempDirectory implements Closeable {

    private final Path path;

    public TempDirectory() throws IOException {

        path = Files.createTempDirectory(this.getClass().getSimpleName());
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void close() throws IOException {
        FileUtils.deleteDirectory(path.toFile());
    }
}
