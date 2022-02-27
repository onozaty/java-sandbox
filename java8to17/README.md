## Java9

* https://openjdk.java.net/projects/jdk9/
* https://docs.oracle.com/javase/jp/9/whatsnew/toc.htm

### ★ JEP 269: Convenience Factory Methods for Collections

コレクションクラスのファクトリメソッドが追加。  
全てイミュータブル(追加/削除/変更不可)なコレクションになる。

```java
// List
List<String> list = List.of("a", "b", "c");

// Set
Set<String> set = Set.of("a", "b", "c");

// Map
Map<Integer, String> map1 = Map.of(1, "a", 2, "b", 3, "c");
// Map#ofEntries を使った方が、キーと値のセットがわかりやすい
Map<Integer, String> map2 = Map.ofEntries(Map.entry(1, "a"), Map.entry(2, "b"), Map.entry(3, "c"));
```

Java9より前は下記のようなコードを書いた。

```java
// List
List<String> list1 = new ArrayList<>();
list1.add("a");
list1.add("b");
list1.add("c");
list1 = Collections.unmodifiableList(list1);

// Arrays#asList だと、要素の追加/削除はできないが、変更はできてしまう
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
```

### ▲ Optionalクラスの拡張

`ifPresent` は値があったときのみ実行されたが、値が無かった時のアクションも同時に実行できる`ifPresentOrElse`が追加された。

```java
AtomicInteger hasValueCounter = new AtomicInteger();
AtomicInteger emptyValueCounter = new AtomicInteger();

Optional<String> value = Optional.ofNullable(null);

value.ifPresentOrElse(
        v -> hasValueCounter.incrementAndGet(),
        () -> emptyValueCounter.incrementAndGet());

assertThat(hasValueCounter.get()).isEqualTo(0);
assertThat(emptyValueCounter.get()).isEqualTo(1);
```

`orElse`と似たようなものとして、`or`が追加された。  
`or` は`Optional`を返す`Supplier`を指定する。  
`Optional`のままデフォルト値で補完するようなときに使えるのかもしれない。

```java
Optional<String> value = Optional.empty();

Optional<String> result = value.or(() -> Optional.of(""));
assertThat(result.get()).isEqualTo("");

// 取り出す際に埋めるような場合には、orElseでも十分
assertThat(value.orElse("")).isEqualTo("");
```

`stream`メソッドで`Stream`に変換できるようになった。  
値がある場合、値が一つ格納された`Stream`、値が無い場合、空の`Stream`になる。

```java
Optional<String> value = Optional.of("a");

long count = value.stream().count();
assertThat(count).isEqualTo(1);
```

```java
Optional<String> value = Optional.empty();

long count = value.stream().count();
assertThat(count).isEqualTo(0);
```

## 参考

* [開発者が喜ぶJDK 9の9つの新機能 - Oracle](https://www.oracle.com/webfolder/technetwork/jp/javamagazine/Java-JA17-Java9Features.pdf)
* [Java 9~17の新機能 / Java 9 ~ 17 Overview \- Speaker Deck](https://speakerdeck.com/maruta/java-9-17-overview?slide=2)