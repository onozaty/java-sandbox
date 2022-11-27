package com.github.onozaty.socialgolfer;

import java.util.HashMap;
import java.util.TreeMap;

public class EvaluateResult {

    private final double score;
    private final HashMap<MemberPair, Integer> matrix;
    private final TreeMap<Integer, Long> matchCountSummary;

    public EvaluateResult(double score, HashMap<MemberPair, Integer> matrix,
            TreeMap<Integer, Long> matchCountSummary) {
        this.score = score;
        this.matrix = matrix;
        this.matchCountSummary = matchCountSummary;
    }

    public double getScore() {
        return score;
    }

    public HashMap<MemberPair, Integer> getMatrix() {
        return matrix;
    }

    public TreeMap<Integer, Long> getMatchCountSummary() {
        return matchCountSummary;
    }

    @Override
    public String toString() {
        return "EvaluateResult [score=" + score + ", matchCountSummary=" + matchCountSummary + "]";
    }
}
