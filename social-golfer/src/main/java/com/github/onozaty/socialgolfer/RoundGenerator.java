package com.github.onozaty.socialgolfer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class RoundGenerator {

    public static Set<Round> generateAllPatternRounds(int groupCount, int memberCountInGroup) {

        int totalMemberCount = groupCount * memberCountInGroup;
        List<Integer> candidateMembers = IntStream.rangeClosed(1, totalMemberCount)
                .mapToObj(Integer::valueOf)
                .toList();

        Set<Group> candidateGroups = generateAllPatternGroups(candidateMembers, memberCountInGroup);

        return generateRounds(
                Collections.emptyList(),
                candidateGroups.stream().toList(),
                groupCount,
                new HashSet<>());
    }

    private static Set<Round> generateRounds(List<Group> currentGroups, List<Group> candidateGroups,
            int groupCount, HashSet<Group> patternCompletedGroups) {

        if (currentGroups.size() == groupCount) {
            return Set.of(new Round(currentGroups));
        }

        HashSet<Round> rounds = new HashSet<>();

        for (Group group : candidateGroups) {

            if (patternCompletedGroups.contains(group)) {
                // 既に全パターンが終わっているグループは対象外
                continue;
            }

            List<Group> nextGroups = new ArrayList<>(currentGroups);
            nextGroups.add(group);

            List<Group> nextCandidateGroups = candidateGroups.stream()
                    .filter(x -> x.notContainsAll(group))
                    .toList();

            rounds.addAll(
                    generateRounds(nextGroups, nextCandidateGroups, groupCount, patternCompletedGroups));

            if (currentGroups.isEmpty()) {
                // 先頭のグループに対する処理の場合、全パターン処理済みのグループとして保存
                patternCompletedGroups.add(group);
            }
        }

        return rounds;
    }

    public static Set<Group> generateAllPatternGroups(List<Integer> candidateMembers, int memberCountInGroup) {
        return generateGroups(Collections.emptyList(), candidateMembers, memberCountInGroup);
    }

    private static Set<Group> generateGroups(
            List<Integer> currentGroupMembers, List<Integer> candidateMembers, int memberCountInGroup) {

        if (currentGroupMembers.size() == memberCountInGroup) {
            return Set.of(new Group(currentGroupMembers));
        }

        HashSet<Group> groups = new HashSet<>();

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
