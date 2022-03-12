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
}
