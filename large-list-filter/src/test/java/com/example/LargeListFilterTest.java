package com.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class LargeListFilterTest {

    private static final int SIZE = 100000;

    private static final List<String> SOURCE;
    private static final List<String> CANDIDATE;

    static {

        SOURCE = IntStream.rangeClosed(1, SIZE)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());

        CANDIDATE = IntStream.rangeClosed(1, SIZE)
                .map(x -> x * 2) // 偶数のみに
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }

    @Test
    public void filterWithSetのテスト() {

        List<String> filterd = filterWithSet(SOURCE, CANDIDATE);

        assertThat(filterd)
                .hasSize(SIZE / 2);
    }

    @Test
    public void filterWithListのテスト() {

        List<String> filterd = filterWithList(SOURCE, CANDIDATE);

        assertThat(filterd)
                .hasSize(SIZE / 2);
    }

    @Test
    public void filterWithRemovedListのテスト() {

        List<String> filterd = filterWithRemovedList(SOURCE, CANDIDATE);

        assertThat(filterd)
                .hasSize(SIZE / 2);
    }

    private <T> List<T> filterWithSet(List<T> source, List<T> candidate) {

        Set<T> candidateSet = new HashSet<>(candidate);

        return source.stream()
                .filter(candidateSet::contains)
                .collect(Collectors.toList());
    }

    private <T> List<T> filterWithList(List<T> source, List<T> candidate) {

        return source.stream()
                .filter(candidate::contains)
                .collect(Collectors.toList());
    }

    private <T> List<T> filterWithRemovedList(List<T> source, List<T> candidate) {

        // 候補から消していくため、sourceに同じ値が複数入っていた場合は正しい結果とならない

        // 元のListを加工しないように複製作成
        List<T> removedCandidate = new ArrayList<T>(candidate);

        return source.stream()
                // 削除できた場合にtrue返すので、含まれることと削除を同時に
                .filter(removedCandidate::remove)
                .collect(Collectors.toList());
    }

}
