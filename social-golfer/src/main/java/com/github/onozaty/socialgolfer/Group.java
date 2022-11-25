package com.github.onozaty.socialgolfer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Group implements Comparable<Group> {

    private final TreeSet<Integer> members;

    public Group(Collection<Integer> members) {
        this.members = new TreeSet<>(members);
    }

    public Group(int... members) {
        this(IntStream.of(members).mapToObj(Integer::valueOf).toList());
    }

    public TreeSet<Integer> getMembers() {
        return members;
    }

    public boolean contains(int member) {
        return members.contains(member);
    }

    public boolean containsAny(Collection<Integer> otherMembers) {
        for (Integer member : members) {
            if (otherMembers.contains(member)) {
                return true;
            }
        }

        return false;
    }

    public boolean notContainsAll(Collection<Integer> otherMembers) {
        return !containsAny(otherMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
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

        Group other = (Group) obj;
        return Objects.equals(members, other.members);
    }

    @Override
    public String toString() {
        return "{" + members.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + "}";
    }

    @Override
    public int compareTo(Group other) {

        if (this.equals(other)) {
            return 0;
        }

        // membersの各要素毎に比較
        // (membersは順序付けされた状態なので、小さいものから比較していくことになる)
        return Arrays.compare(
                members.toArray(Integer[]::new),
                other.members.toArray(Integer[]::new));
    }

}
