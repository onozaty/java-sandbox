package com.enjoyxstudy.tokenize;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class CachedTokenizerTest {

    @Test
    public void tokenize() {

        CachedTokenizer tokenizer = new CachedTokenizer();
        List<String> tokens = tokenizer.tokenize("I have a pen. This is a pen.");

        assertThat(tokens)
                .containsExactly("i", "have", "a", "pen", "this", "is", "a", "pen");

        // 文字列としては同じ、インスタンスとしても同じ(同じ文字列が使いまわされる)
        assertThat(tokens.get(3).equals(tokens.get(7))).isTrue();
        assertThat(tokens.get(3) == tokens.get(7)).isTrue();

        assertThat(tokenizer.getTokenCache())
                .hasSize(tokens.size() - 2); // a と pen は同じオブジェクトになるので
    }
}
