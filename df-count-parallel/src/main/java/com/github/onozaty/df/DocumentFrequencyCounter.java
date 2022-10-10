package com.github.onozaty.df;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class DocumentFrequencyCounter {

    private final Tokenizer tokenizer = new Tokenizer();

    public List<DocumentFrequency> count(List<Path> textFilePaths) {

        Map<String, Long> dfMap = textFilePaths.stream()
                .map(this::tokenizeNouns)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        return dfMap.entrySet().stream()
                .map(x -> new DocumentFrequency(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

    public List<DocumentFrequency> countParallel(List<Path> textFilePaths) {

        Map<String, Long> dfMap = textFilePaths.stream()
                .parallel()
                .map(this::tokenizeNouns)
                .flatMap(Set::stream)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        return dfMap.entrySet().stream()
                .map(x -> new DocumentFrequency(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

    public List<DocumentFrequency> countParallelForEach(List<Path> textFilePaths) {

        ConcurrentHashMap<String, Integer> dfMap = new ConcurrentHashMap<>();

        textFilePaths.stream()
                .parallel()
                .forEach(textFilePath -> {

                    Set<String> nouns = tokenizeNouns(textFilePath);
                    for (String noun : nouns) {
                        dfMap.compute(noun, (key, count) -> {
                            if (count == null) {
                                return 1;
                            }
                            return ++count;
                        });
                    }
                });

        return dfMap.entrySet().stream()
                .map(x -> new DocumentFrequency(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

    public Set<String> tokenizeNouns(Path textFilePath) {

        try {
            String text = new String(Files.readAllBytes(textFilePath), StandardCharsets.UTF_8);
            return tokenizeNouns(text);
        } catch (IOException e) {
            // Streamで扱いやすくするために非チェック例外に
            throw new UncheckedIOException(e);
        }
    }

    public Set<String> tokenizeNouns(String text) {

        return tokenizer.tokenize(text).stream()
                // 名詞だけに絞る
                .filter(token -> token.getPartOfSpeechLevel1().equals("名詞"))
                .map(Token::getSurface)
                .collect(Collectors.toSet());
    }
}
