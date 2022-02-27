package com.github.onozaty.java8to17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Java9 {

    public void jep269() {

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

            // Set
            Set<String> set = Set.of("a", "b", "c");

            // Map
            Map<Integer, String> map1 = Map.of(1, "a", 2, "b", 3, "c");

            // Map#ofEntries を使った方が、キーと値のセットがわかりやすい
            Map<Integer, String> map2 = Map.ofEntries(Map.entry(1, "a"), Map.entry(2, "b"), Map.entry(3, "c"));
        }
    }

}
