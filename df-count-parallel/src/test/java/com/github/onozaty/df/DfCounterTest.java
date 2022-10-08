package com.github.onozaty.df;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class DfCounterTest {

    @Test
    void tokenizeNouns() {
        Set<String> nouns = new DfCounter().tokenizeNouns("今日は良い天気です。校庭で運動します。");

        assertThat(nouns)
                .containsExactlyInAnyOrder("今日", "天気", "校庭", "運動");
    }

}
