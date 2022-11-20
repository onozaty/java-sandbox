package com.github.onozaty.socialgolfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class RoundGeneratorTest {

    @Test
    public void generateAllPatternGroups() {

        // 12名の中から3名をピックアップ(12C3 -> 220)
        List<Integer> candidateMembers = IntStream.rangeClosed(1, 12).mapToObj(Integer::valueOf).toList();
        int memberCountInGroup = 3;

        TreeSet<Group> groups = RoundGenerator.generateAllPatternGroups(candidateMembers, memberCountInGroup);

        assertThat(groups).hasSize(220);
    }

    @Test
    public void generateAllPatternRounds() {

        // 4名毎のグループを3つ作成
        // -> 12C4 * 8C4 -> 495 * 70 = 34,650
        // 組み合わせ関係なしなので、34,650 / 3! -> 34,650 / 1 * 2 * 3 -> 34,650 / 6 = 5,775
        int groupCount = 3;
        int memberCountInGroup = 4;

        TreeSet<Round> rounds = RoundGenerator.generateAllPatternRounds(groupCount, memberCountInGroup);

        assertThat(rounds).hasSize(5775);
    }

}
