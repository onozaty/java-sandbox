package com.enjoyxstudy.tokenize;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class TokenizerTest {

    @Test
    public void tokenize() {

        Tokenizer tokenizer = new Tokenizer();
        List<String> tokens = tokenizer.tokenize("I have a pen. This is a pen.");

        assertThat(tokens)
                .containsExactly("i", "have", "a",  "pen", "this", "is", "a", "pen");
        
        // 文字列としては同じだが、インスタンスは別
        assertThat(tokens.get(3).equals(tokens.get(7))).isTrue();
        assertThat(tokens.get(3) == tokens.get(7)).isFalse();
    }
}
