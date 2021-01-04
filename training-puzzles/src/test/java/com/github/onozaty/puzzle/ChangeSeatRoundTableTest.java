package com.github.onozaty.puzzle;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ChangeSeatRoundTableTest {

    @Test
    public void solove9() {
        int count = ChangeSeatRoundTable.solove(9);

        assertThat(count).isEqualTo(29926);
    }

    @Test
    public void solove10() {
        int count = ChangeSeatRoundTable.solove(10);

        assertThat(count).isEqualTo(315862);
    }
}
