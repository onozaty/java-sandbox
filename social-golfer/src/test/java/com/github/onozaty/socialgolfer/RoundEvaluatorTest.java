package com.github.onozaty.socialgolfer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class RoundEvaluatorTest {

    @Test
    public void evaluateScore() {

        int groupCount = 2;
        int memberCountInGroup = 3;

        RoundEvaluator roundEvaluator = new RoundEvaluator(groupCount, memberCountInGroup);

        {
            List<Round> rounds = List.of(
                    new Round(new Group(1, 2, 3), new Group(4, 5, 6)));

            EvaluateResult result = roundEvaluator.evaluate(rounds);
            assertThat(result.getScore()).isEqualTo(1);
        }

        {
            List<Round> rounds = List.of(
                    new Round(new Group(1, 2, 3), new Group(4, 5, 6)),
                    new Round(new Group(1, 2, 4), new Group(3, 5, 6)));

            EvaluateResult result = roundEvaluator.evaluate(rounds);
            assertThat(result.getScore()).isEqualTo(2 + 7 / (double) 15);
        }

        {
            // 全パターンを当てれば、全て同じマッチ回数になるはず
            Set<Round> allRounds = RoundGenerator.generateAllPatternRounds(2, 3);

            EvaluateResult result = roundEvaluator.evaluate(allRounds.stream().toList());
            assertThat(result.getScore()).isEqualTo(0);
        }
    }

}
