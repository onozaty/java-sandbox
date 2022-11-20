package com.github.onozaty.socialgolfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class RoundGeneratorTest {

    @Test
    public void generateAllPatternGroups() {

        // 12名の中から3名をピックアップ(12C3 -> 220)
        List<Integer> candidateMembers = IntStream.rangeClosed(1, 12).mapToObj(Integer::valueOf).toList();
        int memberCountInGroup = 3;

        List<Group> groups = RoundGenerator.generateAllPatternGroups(candidateMembers, memberCountInGroup);

        assertThat(groups).hasSize(220);
    }

}
