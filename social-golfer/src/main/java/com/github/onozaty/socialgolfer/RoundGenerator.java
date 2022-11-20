package com.github.onozaty.socialgolfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class RoundGenerator {

    public static TreeSet<Round> generateAllPatternRounds(int groupCount, int memberCountInGroup) {

        int totalMemberCount = groupCount * memberCountInGroup;

        List<Integer> candidateMembers = IntStream.rangeClosed(1, totalMemberCount)
                .mapToObj(Integer::valueOf)
                .toList();

        return generateRounds(Collections.emptyList(), candidateMembers, memberCountInGroup);
    }

    public static TreeSet<Round> generateRounds(List<Group> currentGroups, List<Integer> candidateMembers,
            int memberCountInGroup) {

        if (candidateMembers.size() == 0) {
            return new TreeSet<>(List.of(new Round(currentGroups)));
        }

        TreeSet<Round> rounds = new TreeSet<>();

        for (Group group : generateAllPatternGroups(candidateMembers, memberCountInGroup)) {

            List<Group> nextGroups = new ArrayList<>(currentGroups);
            nextGroups.add(group);

            List<Integer> nextCandidateMembers = candidateMembers.stream()
                    .filter(Predicate.not(group::hasMember))
                    .toList();

            rounds.addAll(generateRounds(nextGroups, nextCandidateMembers, memberCountInGroup));
        }

        return rounds;
    }

    public static TreeSet<Group> generateAllPatternGroups(List<Integer> candidateMembers, int memberCountInGroup) {
        return generateGroups(Collections.emptyList(), candidateMembers, memberCountInGroup);
    }

    private static TreeSet<Group> generateGroups(
            List<Integer> currentGroupMembers, List<Integer> candidateMembers, int memberCountInGroup) {

        if (currentGroupMembers.size() == memberCountInGroup) {
            return new TreeSet<>(List.of(new Group(currentGroupMembers)));
        }

        TreeSet<Group> groups = new TreeSet<>();

        for (int i = 0; i < candidateMembers.size(); i++) {

            Integer member = candidateMembers.get(i);
            List<Integer> nextGroupMembers = new ArrayList<>(currentGroupMembers);
            nextGroupMembers.add(member);

            // 現在の位置から後ろにあるもの
            List<Integer> nextCandidateMembers = candidateMembers.subList(i + 1, candidateMembers.size());

            groups.addAll(generateGroups(nextGroupMembers, nextCandidateMembers, memberCountInGroup));
        }

        return groups;
    }

}
