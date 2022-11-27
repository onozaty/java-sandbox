package com.github.onozaty.socialgolfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class MemberPair {

    private final int first;
    private final int second;

    public MemberPair(int first, int second) {

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
        MemberPair other = (MemberPair) obj;
        return first == other.first && second == other.second;
    }

    @Override
    public String toString() {
        return "MemberPair [first=" + first + ", second=" + second + "]";
    }

    public static List<MemberPair> of(int totalMemberCount) {

        List<Integer> members = IntStream.rangeClosed(1, totalMemberCount)
                .mapToObj(Integer::valueOf)
                .toList();

        ArrayList<MemberPair> pairs = new ArrayList<>();

        for (int i = 0; i < members.size() - 1; i++) {
            for (int j = i + 1; j < members.size(); j++) {
                pairs.add(new MemberPair(members.get(i), members.get(j)));
            }
        }

        return pairs;
    }
}
