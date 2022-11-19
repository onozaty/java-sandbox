package com.github.onozaty.socialgolfer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class RoundTest {

    @Test
    public void test_equals() {

        Group group1 = new Group(1, 2, 3);
        Group group2 = new Group(4, 5, 6);
        Group group3 = new Group(7, 8, 9);
        Group group4 = new Group(2, 4, 6);

        Round round1 = new Round(group1, group2, group3);
        Round round2 = new Round(group2, group3, group1);
        Round round3 = new Round(group1, group2, group4);
        Round round4 = new Round(group1, group2);

        assertThat(round1.equals(round2)).isTrue(); // 順序が違っても同じとして扱われること
        assertThat(round1.equals(round3)).isFalse();
        assertThat(round1.equals(round4)).isFalse();
    }

    @Test
    public void test_toString() {

        Group group1 = new Group(1, 2, 3);
        Group group2 = new Group(4, 5, 6);
        Group group3 = new Group(7, 8, 9);

        Round round1 = new Round(group1, group2, group3);
        Round round2 = new Round(group2, group3, group1);
        Round round3 = new Round(group1, group2);

        assertThat(round1.toString()).isEqualTo("{1, 2, 3}, {4, 5, 6}, {7, 8, 9}");
        assertThat(round2.toString()).isEqualTo("{1, 2, 3}, {4, 5, 6}, {7, 8, 9}");
        assertThat(round3.toString()).isEqualTo("{1, 2, 3}, {4, 5, 6}");
    }

    @Test
    public void test_compareTo() {

        Group group1 = new Group(1, 2, 3);
        Group group2 = new Group(4, 5, 6);
        Group group3 = new Group(7, 8, 9);
        Group group4 = new Group(5, 6);

        Round round1 = new Round(group1, group2, group3);
        Round round2 = new Round(group2, group3, group1);
        Round round3 = new Round(group1, group2);
        Round round4 = new Round(group1, group3, group4);

        assertThat(round1.compareTo(round2)).isEqualTo(0);
        assertThat(round1.compareTo(round3)).isEqualTo(1);
        assertThat(round1.compareTo(round4)).isEqualTo(-1);
    }
}
