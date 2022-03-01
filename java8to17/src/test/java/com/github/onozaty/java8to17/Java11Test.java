package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Java11Test {

    @Test
    public void string_repeat() {

        String text = "a".repeat(10);
        assertThat(text).isEqualTo("aaaaaaaaaa");

    }

    @Test
    public void string_strip() {

        String text = "\n\u3000 a \u3000\n";

        assertThat(text.strip()).isEqualTo("a");
        assertThat(text.stripLeading()).isEqualTo("a \u3000\n");
        assertThat(text.stripTrailing()).isEqualTo("\n\u3000 a");

        assertThat(text.trim()).isEqualTo("\u3000 a \u3000"); // 全角スペースは消えない
    }

    @Test
    public void string_isBlank() {

        assertThat(" ".isBlank()).isTrue();
        assertThat("\n\t\u3000".isBlank()).isTrue();
    }
}
