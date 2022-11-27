package com.github.onozaty.socialgolfer;

import java.util.List;

public class EvaluatedRounds implements Comparable<EvaluatedRounds> {

    private final EvaluateResult result;
    private final List<Round> rounds;

    public EvaluatedRounds(EvaluateResult result, List<Round> rounds) {
        this.result = result;
        this.rounds = rounds;
    }

    public EvaluateResult getResult() {
        return result;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Group> getGroups() {
        return rounds.stream()
                .flatMap(round -> round.getGroups().stream())
                .toList();
    }

    @Override
    public int compareTo(EvaluatedRounds other) {

        if (result.getScore() == other.getResult().getScore()) {
            return rounds.get(0).compareTo(other.rounds.get(0));
        }

        return result.getScore() < other.getResult().getScore() ? -1 : 1;
    }

    @Override
    public String toString() {
        return "EvaluatedRounds [result=" + result + ", rounds=" + rounds + "]";
    }
}