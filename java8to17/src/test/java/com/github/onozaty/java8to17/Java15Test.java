package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Java15Test {

    @Test
    public void text_block() {

        {
            String text = """
                    1行目
                    2行目
                    """;

            assertThat(text)
                    .isEqualTo("1行目\n2行目\n");
        }

        {
            String text = """
                    1行目
                      2行目(インデント)
                    """;

            assertThat(text)
                    .isEqualTo("1行目\n  2行目(インデント)\n");
        }

        {
            String text = """
                    1行目
                    2行目""";

            assertThat(text)
                    .isEqualTo("1行目\n2行目");
        }

        {
            String text = """
                    1行目\
                    2行目\
                    """;

            assertThat(text)
                    .isEqualTo("1行目2行目");
        }

        {
            String text = """
                    1行目 \s
                    2行目\s
                    """;

            assertThat(text)
                    .isEqualTo("1行目  \n2行目 \n");
        }
    }
}
