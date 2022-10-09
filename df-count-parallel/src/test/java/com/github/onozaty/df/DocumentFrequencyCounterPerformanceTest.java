package com.github.onozaty.df;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class DocumentFrequencyCounterPerformanceTest {

    @Test
    public void count() throws IOException {

        List<Path> textFilePaths = getTextFilePaths();

        List<DocumentFrequency> documentFrequencies = new DocumentFrequencyCounter().count(textFilePaths);

        assertThat(documentFrequencies)
                .isNotEmpty();
    }

    @Test
    public void countParallel() throws IOException {

        List<Path> textFilePaths = getTextFilePaths();

        List<DocumentFrequency> documentFrequencies = new DocumentFrequencyCounter().countParallel(textFilePaths);

        assertThat(documentFrequencies)
                .isNotEmpty();
    }

    private List<Path> getTextFilePaths() throws IOException {

        String targetDirPath = ""; // テストデータとなるテキストファイルが配置されたディレクトリを指定

        return Files.walk(Paths.get(targetDirPath))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
    }
}
