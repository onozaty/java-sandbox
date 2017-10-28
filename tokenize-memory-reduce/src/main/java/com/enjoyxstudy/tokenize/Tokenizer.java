package com.enjoyxstudy.tokenize;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer {

    public static void main(String[] args) throws IOException {

        Path inputFilePath = Paths.get(args[0]);

        long startNanoTime = System.nanoTime();

        System.out.printf(
                "file size: %,d byte" + System.lineSeparator(),
                Files.size(inputFilePath));

        try (Stream<String> lines = Files.lines(inputFilePath, StandardCharsets.UTF_8)) {

            Tokenizer tokenizer = new Tokenizer();
            List<String> tokens = lines.map(line -> tokenizer.tokenize(line))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            System.out.printf(
                    "time: %,.3f ms / token size: %,d",
                    TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startNanoTime) / 1000f,
                    tokens.size());
        }
    }

    public List<String> tokenize(String text) {

        String[] tokens = text.split("[^a-zA-Z]+");

        return Stream.of(tokens)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
