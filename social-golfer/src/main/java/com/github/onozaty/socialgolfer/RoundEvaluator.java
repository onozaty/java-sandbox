package com.github.onozaty.socialgolfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RoundEvaluator {

    private final List<Pair> pairs;

    public RoundEvaluator(List<Integer> members) {

        ArrayList<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < members.size() - 1; i++) {
            for (int j = i + 1; j < members.size(); j++) {
                pairs.add(new Pair(members.get(i), members.get(j)));
            }
        }

        this.pairs = Collections.unmodifiableList(pairs);
    }

    public int evaluate(List<Round> rounds) {

        // 0でいったん初期化
        HashMap<Pair, Integer> matrix = new HashMap<>();
        this.pairs.forEach(x -> matrix.put(x, 0));

        rounds.forEach(x -> applyRound(matrix, x));

        // 最小、最大マッチ件数を取得
        int min = matrix.values().stream().min(Comparator.naturalOrder()).get();
        int max = matrix.values().stream().max(Comparator.naturalOrder()).get();

        // 差を評価値として利用
        // (0に近いほど良い値)
        return max - min;
    }

    private void applyRound(HashMap<Pair, Integer> matrix, Round round) {
        round.getGroups().forEach(x -> applyGroup(matrix, x));
    }

    private void applyGroup(HashMap<Pair, Integer> matrix, Group group) {

        List<Integer> members = group.getMembers().stream().toList();

        for (int i = 0; i < members.size() - 1; i++) {
            for (int j = i + 1; j < members.size(); j++) {
                // インクリメントしていく
                matrix.compute(new Pair(members.get(i), members.get(j)), (pair, count) -> ++count);
            }
        }
    }

    private static class Pair {
        private final int first;
        private final int second;

        private Pair(int first, int second) {

            // 数の小さい順にfirst, secondと設定
            if (first < second) {
                this.first = first;
                this.second = second;
            } else {
                this.first = second;
                this.second = first;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Pair other = (Pair) obj;
            return first == other.first && second == other.second;
        }

        @Override
        public String toString() {
            return "Pair [first=" + first + ", second=" + second + "]";
        }
    }
}
