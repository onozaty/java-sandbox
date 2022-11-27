package com.github.onozaty.socialgolfer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RoundEvaluator {

    private final List<MemberPair> pairs;

    public RoundEvaluator(int groupCount, int memberCountInGroup) {

        int totalMemberCount = groupCount * memberCountInGroup;
        this.pairs = Collections.unmodifiableList(MemberPair.of(totalMemberCount));
    }

    public EvaluateResult evaluate(List<Round> rounds) {

        // 0でいったん初期化
        HashMap<MemberPair, Integer> matrix = new HashMap<>();
        this.pairs.forEach(x -> matrix.put(x, 0));

        rounds.forEach(x -> applyRound(matrix, x));

        TreeMap<Integer, Long> matchCountSummary =
                new TreeMap<>(
                        matrix.values().stream()
                                .collect(Collectors.groupingBy(x -> x, Collectors.counting())));

        // 最小、最大マッチ件数を取得
        int min = matchCountSummary.firstKey();
        int max = matchCountSummary.lastKey();

        // 差を評価値として利用
        // (0に近いほど良い値)
        double score = max - min;

        if (score >= 2) {
            // 差が2以上ある場合、最大と最小マッチとなっている数の割合をプラス
            // (中央に集約されていた方がよりよいので、最大と最小の数が多い＝スコアを下げる)
            long count = matchCountSummary.firstEntry().getValue()
                    + matchCountSummary.lastEntry().getValue();

            score += count / (double) matrix.size();
        }

        return new EvaluateResult(score, matrix, matchCountSummary);
    }

    private void applyRound(HashMap<MemberPair, Integer> matrix, Round round) {
        round.getGroups().forEach(x -> applyGroup(matrix, x));
    }

    private void applyGroup(HashMap<MemberPair, Integer> matrix, Group group) {

        List<Integer> members = group.getMembers().stream().toList();

        for (int i = 0; i < members.size() - 1; i++) {
            for (int j = i + 1; j < members.size(); j++) {
                // インクリメントしていく
                matrix.compute(new MemberPair(members.get(i), members.get(j)), (pair, count) -> ++count);
            }
        }
    }

}
