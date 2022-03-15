package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.Test;

public class Java16Test {
    @Test
    public void patternmatch_instanceof() {

        Object value = 10;

        if (value instanceof Integer num) {
            assertThat(num).isEqualTo(10);
        } else {
            fail();
        }
    }

    @Test
    public void record() {

        Rectangle rectangle = new Rectangle(1, 2);

        assertThat(rectangle.length())
                .isEqualTo(1);
        assertThat(rectangle.width())
                .isEqualTo(2);
        assertThat(rectangle.toString())
                .isEqualTo("Rectangle[length=1, width=2]");

        Rectangle other = new Rectangle(1, 2);

        assertThat(rectangle.hashCode())
                .isEqualTo(other.hashCode());
        assertThat(rectangle.equals(other))
                .isTrue();

        assertThat(rectangle.area()).isEqualTo(2);
    }

    @Test
    public void stream_toList() {

        List<String> list = Stream.of("a", "b").toList();

        assertThat(list)
                .containsExactly("a", "b");

        assertThatThrownBy(() -> {

            list.set(0, "x");

        }).isInstanceOf(UnsupportedOperationException.class);

    }

    @Test
    public void stream_mapMulti() {

        List<String> upperChars =
                Stream.of("abc", "xyz")
                        .mapMulti((String str, Consumer<String> consumer) -> {
                            for (char c : str.toCharArray()) {
                                consumer.accept(String.valueOf(c).toUpperCase());
                            }
                        })
                        .toList();

        assertThat(upperChars)
                .containsExactly("A", "B", "C", "X", "Y", "Z");

    }
}
