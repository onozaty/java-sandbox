package com.github.onozaty.df;

import java.util.Set;
import java.util.stream.Collectors;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

public class DfCounter {

    public Set<String> tokenizeNouns(String text) {

        Tokenizer tokenizer = new Tokenizer();

        return tokenizer.tokenize(text).stream()
                // 名詞だけに絞る
                .filter(token -> token.getPartOfSpeechLevel1().equals("名詞"))
                .map(Token::getSurface)
                .collect(Collectors.toSet());
    }
}
