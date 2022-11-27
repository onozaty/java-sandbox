package com.github.onozaty.socialgolfer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Round implements Comparable<Round> {

    private final TreeSet<Group> groups;

    public Round(Collection<Group> groups) {
        this.groups = new TreeSet<>(groups);
    }

    public Round(Group... groups) {
        this(Stream.of(groups).toList());
    }

    public TreeSet<Group> getGroups() {
        return groups;
    }

    public boolean contains(Group group) {
        return groups.contains(group);
    }

    public boolean containsAny(Collection<Group> otherGroups) {
        for (Group group : groups) {
            if (otherGroups.contains(group)) {
                return true;
            }
        }

        return false;
    }

    public boolean notContainsAll(Collection<Group> otherGroups) {
        return !containsAny(otherGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups);
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

        Round other = (Round) obj;
        return Objects.equals(groups, other.groups);
    }

    @Override
    public String toString() {
        return "{" + groups.stream()
                .map(Group::toString)
                .collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public int compareTo(Round other) {

        if (this.equals(other)) {
            return 0;
        }

        // groupsの各要素毎に比較
        // (groupsは順序付けされた状態なので、小さいものから比較していくことになる)
        return Arrays.compare(
                groups.toArray(Group[]::new),
                other.groups.toArray(Group[]::new));
    }

}
