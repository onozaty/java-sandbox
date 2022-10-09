package com.github.onozaty.df;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class DocumentFrequencyCounter {

    private final Tokenizer tokenizer = new Tokenizer();

    public List<DocumentFrequency> count(List<Path> textFilePaths) throws IOException {

        HashMap<String, Integer> dfMap = new HashMap<>();

        for (Path textFilePath : textFilePaths) {
            String text = new String(Files.readAllBytes(textFilePath), StandardCharsets.UTF_8);

            Set<String> nouns = tokenizeNouns(text);
            for (String noun : nouns) {
                dfMap.compute(noun, (key, count) -> {
                    if (count == null) {
                        return 1;
                    }
                    return ++count;
                });
            }
        }

        return dfMap.entrySet().stream()
                .map(x -> new DocumentFrequency(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }

    public Set<String> tokenizeNouns(String text) {

        return tokenizer.tokenize(text).stream()
                // 名詞だけに絞る
                .filter(token -> token.getPartOfSpeechLevel1().equals("名詞"))
                .map(Token::getSurface)
                .collect(Collectors.toSet());
    }
}
