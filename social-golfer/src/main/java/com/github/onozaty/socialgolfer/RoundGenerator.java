package com.github.onozaty.socialgolfer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class RoundGenerator {

    public static List<Round> generateAllPatternRounds(int groupCount, int memberCountInGroup) {

        int totalMemberCount = groupCount * memberCountInGroup;

        List<Integer> members = IntStream.rangeClosed(1, totalMemberCount)
                .mapToObj(Integer::valueOf)
                .toList();

        List<Group> allPatternGroups = generateAllPatternGroups(members, memberCountInGroup);

        return null;
    }

    public static List<Group> generateAllPatternGroups(List<Integer> candidateMembers, int memberCountInGroup) {
        return generateGroups(List.of(), candidateMembers, memberCountInGroup);
    }

    private static List<Group> generateGroups(
            List<Integer> currentGroupMembers, List<Integer> candidateMembers, int memberCountInGroup) {

        if (currentGroupMembers.size() == memberCountInGroup) {
            return List.of(new Group(currentGroupMembers));
        }

        List<Group> groups = new ArrayList<>();

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
