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

    @Test
    public void string_formatted() {

        {
            String formatText = "%,d円です。";

            String text = String.format(formatText, 1234);

            assertThat(text).isEqualTo("1,234円です。");
        }

        {
            String formatText = "%,d円です。";

            String text = formatText.formatted(1234);

            assertThat(text).isEqualTo("1,234円です。");
        }

        {
            String text = """
                    %sさん。
                    金額は%,d円です。
                    """.formatted("太郎", 1234);

            assertThat(text).isEqualTo("太郎さん。\n金額は1,234円です。\n");
        }
    }

    @Test
    public void string_stripIndent() {

        {
            String text = " 1\n  2\n   3";

            assertThat(text.stripIndent()).isEqualTo("1\n 2\n  3");
        }
    }

    @Test
    public void string_translateEscapes() {

        {
            String text = "a\\n\\tb";

            assertThat(text.translateEscapes()).isEqualTo("a\n\tb");
        }
    }
}
