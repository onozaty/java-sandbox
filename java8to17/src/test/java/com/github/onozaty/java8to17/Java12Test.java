package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class Java12Test {

    @Test
    public void string_indent() {

        String base = "1\n2\n\n";
        String indented = base.indent(2);

        assertThat(indented).isEqualTo("  1\n  2\n  \n");
    }

    @Test
    public void string_transform() {

        {
            int num = "1".transform(Integer::parseInt);
            assertThat(num).isEqualTo(1);
        }

        {
            int num = "12#34".transform(this::clean).transform(Integer::valueOf);
            assertThat(num).isEqualTo(1234);
        }
    }

    private String clean(String str) {
        return str.replaceAll("[^0-9]", "");
    }
}
