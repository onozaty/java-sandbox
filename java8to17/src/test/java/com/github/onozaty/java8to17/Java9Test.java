package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

public class Java9Test {

    @Test
    public void collectionsFactory_jep269() {

        // Java9 より前
        {
            // List
            List<String> list1 = new ArrayList<>();
            list1.add("a");
            list1.add("b");
            list1.add("c");
            list1 = Collections.unmodifiableList(list1);

            // Arrays#asList だと、要素の追加/削除はできないが、変更はできる
            List<String> list2 = Arrays.asList("a", "b", "c");
            list2.set(1, "bb");

            // Set
            Set<String> set = new HashSet<>();
            set.add("a");
            set.add("b");
            set.add("c");
            set = Collections.unmodifiableSet(set);

            // Map
            Map<Integer, String> map = new HashMap<>();
            map.put(1, "a");
            map.put(2, "b");
            map.put(3, "c");
            map = Collections.unmodifiableMap(map);
        }

        // Java9
        {
            // 全てイミュータブルになる(追加、削除、変更が不可)

            // List
            List<String> list = List.of("a", "b", "c");
            assertThat(list).containsExactly("a", "b", "c");

            // Set
            Set<String> set = Set.of("a", "b", "c");
            assertThat(set).containsExactlyInAnyOrder("a", "b", "c");

            // Map
            Map<Integer, String> map1 = Map.of(1, "a", 2, "b", 3, "c");
            assertThat(map1)
                    .hasSize(3)
                    .containsEntry(1, "a")
                    .containsEntry(2, "b")
                    .containsEntry(3, "c");

            // Map#ofEntries を使った方が、キーと値のセットがわかりやすい
            Map<Integer, String> map2 = Map.ofEntries(Map.entry(1, "a"), Map.entry(2, "b"), Map.entry(3, "c"));
            assertThat(map2)
                    .hasSize(3)
                    .containsEntry(1, "a")
                    .containsEntry(2, "b")
                    .containsEntry(3, "c");
        }
    }

    @Test
    public void optional() {

        {
            AtomicInteger hasValueCounter = new AtomicInteger();
            AtomicInteger emptyValueCounter = new AtomicInteger();

            Optional<String> value = Optional.ofNullable(null);

            value.ifPresentOrElse(
                    v -> hasValueCounter.incrementAndGet(),
                    () -> emptyValueCounter.incrementAndGet());

            assertThat(hasValueCounter.get()).isEqualTo(0);
            assertThat(emptyValueCounter.get()).isEqualTo(1);
        }

        {
            Optional<String> value = Optional.empty();

            Optional<String> result = value.or(() -> Optional.of(""));

            assertThat(result.get()).isEqualTo("");

            // 取り出す際に埋めるような場合には、orElseでも十分
            assertThat(value.orElse("")).isEqualTo("");
        }

        {
            Optional<String> value = Optional.of("a");

            long count = value.stream().count();
            assertThat(count).isEqualTo(1);
        }

        {
            Optional<String> value = Optional.empty();

            long count = value.stream().count();
            assertThat(count).isEqualTo(0);
        }
    }

    @Test
    public void stream() {

        {
            // 2の乗数で100以下のものを求める
            int[] results = IntStream.iterate(2, x -> x * 2)
                    .takeWhile(x -> x <= 100)
                    .toArray();

            assertThat(results)
                    .containsExactly(2, 4, 8, 16, 32, 64);
        }

        {
            // 2の乗数で100以上、1000以下のものを求める
            int[] results = IntStream.iterate(2, x -> x * 2)
                    .dropWhile(x -> x < 100)
                    .takeWhile(x -> x <= 1000)
                    .toArray();

            assertThat(results)
                    .containsExactly(128, 256, 512);
        }

        {
            long count = Stream.ofNullable("a").count();
            assertThat(count).isEqualTo(1);
        }

        {
            long count = Stream.ofNullable(null).count();
            assertThat(count).isEqualTo(0);
        }
    }

    @Test
    public void inputStream_readAllBytes() throws IOException {

        String text = "a".repeat(1000);
        try (InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))) {

            byte[] results = inputStream.readAllBytes();

            assertThat(results).isEqualTo(text.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void inputStream_transferTo() throws IOException {

        String text = "a".repeat(1000);
        try (InputStream inputStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            inputStream.transferTo(outputStream);

            assertThat(outputStream.toByteArray()).isEqualTo(text.getBytes(StandardCharsets.UTF_8));
        }
    }
}
