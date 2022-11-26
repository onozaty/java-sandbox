package com.github.onozaty.socialgolfer;

import java.util.List;
import java.util.Objects;

public class EvaluatedRounds implements Comparable<EvaluatedRounds> {

    private final int score;
    private final List<Round> rounds;

    public EvaluatedRounds(int score, List<Round> rounds) {
        this.score = score;
        this.rounds = rounds;
    }

    public int getScore() {
        return score;
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
    public int hashCode() {
        return Objects.hash(rounds, score);
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
        EvaluatedRounds other = (EvaluatedRounds) obj;
        return Objects.equals(rounds, other.rounds) && score == other.score;
    }

    @Override
    public int compareTo(EvaluatedRounds other) {
        if (this.equals(other)) {
            return 0;
        }

        if (score == other.score) {
            return rounds.get(0).compareTo(other.rounds.get(0));
        }

        return score - other.score;
    }
}