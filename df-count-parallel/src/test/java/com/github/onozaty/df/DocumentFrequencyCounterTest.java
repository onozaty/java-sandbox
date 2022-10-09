package com.github.onozaty.df;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class DocumentFrequencyCounterTest {

    @Test
    public void count() throws URISyntaxException {

        List<Path> textFilePaths = Arrays.asList(
                getResourcePath("test1.txt"),
                getResourcePath("test2.txt"),
                getResourcePath("test3.txt"),
                getResourcePath("test4.txt"));

        List<DocumentFrequency> documentFrequencies = new DocumentFrequencyCounter().count(textFilePaths);

        assertThat(documentFrequencies)
                .containsExactlyInAnyOrder(
                        new DocumentFrequency("今日", 2),
                        new DocumentFrequency("天気", 2),
                        new DocumentFrequency("晴れ", 2),
                        new DocumentFrequency("明日", 3),
                        new DocumentFrequency("予報", 1),
                        new DocumentFrequency("のち", 1),
                        new DocumentFrequency("雨", 2),
                        new DocumentFrequency("小学校", 1),
                        new DocumentFrequency("入学", 1),
                        new DocumentFrequency("式", 1),
                        new DocumentFrequency("テニス", 1),
                        new DocumentFrequency("部", 1),
                        new DocumentFrequency("大会", 1),
                        new DocumentFrequency("トーナメント", 1),
                        new DocumentFrequency("方式", 1));
    }

    @Test
    public void countParallel() throws URISyntaxException {

        List<Path> textFilePaths = Arrays.asList(
                getResourcePath("test1.txt"),
                getResourcePath("test2.txt"),
                getResourcePath("test3.txt"),
                getResourcePath("test4.txt"));

        List<DocumentFrequency> documentFrequencies = new DocumentFrequencyCounter().countParallel(textFilePaths);

        assertThat(documentFrequencies)
                .containsExactlyInAnyOrder(
                        new DocumentFrequency("今日", 2),
                        new DocumentFrequency("天気", 2),
                        new DocumentFrequency("晴れ", 2),
                        new DocumentFrequency("明日", 3),
                        new DocumentFrequency("予報", 1),
                        new DocumentFrequency("のち", 1),
                        new DocumentFrequency("雨", 2),
                        new DocumentFrequency("小学校", 1),
                        new DocumentFrequency("入学", 1),
                        new DocumentFrequency("式", 1),
                        new DocumentFrequency("テニス", 1),
                        new DocumentFrequency("部", 1),
                        new DocumentFrequency("大会", 1),
                        new DocumentFrequency("トーナメント", 1),
                        new DocumentFrequency("方式", 1));
    }

    @Test
    public void tokenizeNouns() {
        Set<String> nouns = new DocumentFrequencyCounter().tokenizeNouns("今日は良い天気です。校庭で運動します。");

        assertThat(nouns)
                .containsExactlyInAnyOrder("今日", "天気", "校庭", "運動");
    }

    private Path getResourcePath(String fileName) throws URISyntaxException {

        return Paths.get(getClass().getResource(fileName).toURI());
    }
}
