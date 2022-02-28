package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Java10Test {

    @Test
    public void copyOf() {

        {
            var base = List.of("a", "b", "c");

            // baseは変更不可なので、base自体が返される
            var copied = List.copyOf(base);
            assertThat(copied == base).isTrue();
        }

        {
            var base = Arrays.asList("a", "b", "c");

            // baseは変更可能なので、変更不可とした複製が返される
            var copied = List.copyOf(base);
            assertThat(copied == base).isFalse();
        }

    }
}
