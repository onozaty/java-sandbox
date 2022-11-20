package com.github.onozaty.socialgolfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class RoundEvaluatorTest {

    @Test
    public void evaluate() {

        List<Integer> members = IntStream.rangeClosed(1, 6).mapToObj(Integer::valueOf).toList();

        RoundEvaluator roundEvaluator = new RoundEvaluator(members);

        {
            List<Round> rounds = List.of(
                    new Round(new Group(1, 2, 3), new Group(4, 5, 6)));

            int value = roundEvaluator.evaluate(rounds);
            assertThat(value).isEqualTo(1);
        }

        {
            List<Round> rounds = List.of(
                    new Round(new Group(1, 2, 3), new Group(4, 5, 6)),
                    new Round(new Group(1, 2, 4), new Group(3, 5, 6)));

            int value = roundEvaluator.evaluate(rounds);
            assertThat(value).isEqualTo(2);
        }

        {
            // 全パターンを当てれば、全て同じマッチ回数になるはず
            Set<Round> allRounds = RoundGenerator.generateAllPatternRounds(2, 3);

            int value = roundEvaluator.evaluate(allRounds.stream().toList());
            assertThat(value).isEqualTo(0);
        }
    }

}
