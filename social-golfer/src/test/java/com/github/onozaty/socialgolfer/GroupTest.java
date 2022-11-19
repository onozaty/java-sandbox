package com.github.onozaty.socialgolfer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class GroupTest {

    @Test
    public void test_equals() {

        Group group1 = new Group(1, 2, 3);
        Group group2 = new Group(2, 3, 1);
        Group group3 = new Group(1, 2);
        Group group4 = new Group(1, 2, 4);

        assertThat(group1.equals(group2)).isTrue(); // 順序が違っても同じとして扱われること
        assertThat(group1.equals(group3)).isFalse();
        assertThat(group1.equals(group4)).isFalse();
    }

    @Test
    public void test_toString() {

        Group group1 = new Group(1, 2, 3);
        Group group2 = new Group(2, 3, 1);
        Group group3 = new Group(1, 2);

        assertThat(group1.toString()).isEqualTo("{1, 2, 3}");
        assertThat(group2.toString()).isEqualTo("{1, 2, 3}");
        assertThat(group3.toString()).isEqualTo("{1, 2}");
    }

    @Test
    public void test_compareTo() {

        Group group1 = new Group(1, 2, 3);
        Group group2 = new Group(2, 3, 1);
        Group group3 = new Group(1, 2, 4);
        Group group4 = new Group(1, 2);

        assertThat(group1.compareTo(group2)).isEqualTo(0);
        assertThat(group1.compareTo(group3)).isEqualTo(-1);
        assertThat(group1.compareTo(group4)).isEqualTo(1);
    }
}
