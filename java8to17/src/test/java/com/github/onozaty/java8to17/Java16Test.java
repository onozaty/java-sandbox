package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

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

}
