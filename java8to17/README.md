## Java9

* https://openjdk.java.net/projects/jdk9/
* https://docs.oracle.com/javase/jp/9/whatsnew/toc.htm

### ★ JEP 269: Convenience Factory Methods for Collections

コレクションクラスのファクトリメソッドが追加されました。  
全てイミュータブル(追加/削除/変更不可)なコレクションになります。

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

Java9より前は下記のようなコードを書く必要がありました。

```java
// List
List<String> list1 = new ArrayList<>();
list1.add("a");
list1.add("b");
list1.add("c");
list1 = Collections.unmodifiableList(list1);

// Arrays.asList だと、要素の追加/削除はできないが、変更はできてしまう
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

### ▲ Optionalクラスの改善

`ifPresent` は値があったときのみ実行されましたが、値が無かった時のアクションも同時に実行できる`ifPresentOrElse`が追加されました。  

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

`orElse`と似たようなものとして、`or`が追加されました。  
`or` は`Optional`を返す`Supplier`を指定します。  
`Optional`のままデフォルト値で補完するようなときに使えるのかもしれません。

```java
Optional<String> value = Optional.empty();

Optional<String> result = value.or(() -> Optional.of(""));
assertThat(result.get()).isEqualTo("");

// 取り出す際に埋めるような場合には、orElseでも十分
assertThat(value.orElse("")).isEqualTo("");
```

`stream`メソッドで`Stream`に変換できるようになりました。  
値がある場合、値が一つ格納された`Stream`、値が無い場合、空の`Stream`になります。

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

### ▲ Stream APIの改善

`takeWhile` で指定した条件を満たす間のデータを対象とします。

```java
// 2の乗数で100以下のものを求める
int[] results = IntStream.iterate(2, x -> x * 2)
        .takeWhile(x -> x <= 100)
        .toArray();

assertThat(results)
        .containsExactly(2, 4, 8, 16, 32, 64);
```

`dropWhile` で指定した条件を満たす間のデータを対象外とします。

```java
// 2の乗数で100以上、1000以下のものを求める
int[] results = IntStream.iterate(2, x -> x * 2)
        .dropWhile(x -> x < 100)
        .takeWhile(x -> x <= 1000)
        .toArray();

assertThat(results)
        .containsExactly(128, 256, 512);
```

`ofNullable` は、nullの場合は空の`Stream`を、null以外の場合は指定された値を持つ要素数1の`Stream`を返却します。

```java
long count = Stream.ofNullable("a").count();
assertThat(count).isEqualTo(1);
```

```java
long count = Stream.ofNullable(null).count();
assertThat(count).isEqualTo(0);
```

### ▲ privateインタフェース・メソッドのサポート

インターフェースでprivateメソッドが定義できるようになりました。

```java
public interface Hoge {

    private void fuga() {
    }
}
```

privateなので実装クラスからは参照できません。インターフェースのデフォルトメソッドやstaticメソッドからの参照になります。

### ▲ InputStream#readAllBytes、transferTo の追加

`InputStream`に全てを読み込むメソッドの`readAllBytes`が追加されました。

```java
byte[] results = inputStream.readAllBytes();
```

ファイルの場合には、`Files.readAllBytes`がありましたが、それが`InputStream`に対しても同じようなことが出来るようになりました。

また、内容を`OutputStream`に書き込む`transferTo`も追加されました。

```java
inputStream.transferTo(outputStream);
```

## Java10

* https://openjdk.java.net/projects/jdk/10/

### ▲ ローカル変数の型推論

`var`という構文が追加され、ローカル変数の場合には`var`で型が省略できるようになりました。

```java
var num = 1;
var list = List.of("a");
```

右辺で型が自明なもの(newしていたりなど)では良いと思いますが、メソッドの戻りの代入などで`var`を使ってしまうと、型がわかりずらくなるので、積極的に使わなくて良いと考えています。

```java
// 型が自明(右辺でわかる)
var calculator = new Calculator();
var list = List.of("a");

// 型がわかりずらくなる
var result = compute();
```

### ▲ List、Set、Mapの変更不可なコピー作成

`List`、`Set`、`Map`に`copyOf`というstaticメソッドが追加され、変更不可な複製が生成できるようになりました。

コピー元が変更不可な場合、複製を作る必要がないので、コピー元のインスタンス自体が返却されます。

```java
var base = List.of("a", "b", "c");

// baseは変更不可なので、base自体が返される
var copied = List.copyOf(base);
assertThat(copied == base).isTrue();
```

```java
var base = Arrays.asList("a", "b", "c");

// baseは変更可能なので、変更不可とした複製が返される
var copied = List.copyOf(base);
assertThat(copied == base).isFalse();
```

## Java11

* https://openjdk.java.net/projects/jdk/11/

### ★ String#repeat の追加

`String#repeat`で文字列を指定回数繰り返した文字列を作成できるようになりました。

```java
String text = "a".repeat(10);
assertThat(text).isEqualTo("aaaaaaaaaa");
```

### ▲ String#strip、stripLeading、stripTrailing の追加

`String#trim`、`trimLeft`、`trimRight` と同じようなものですが、全角スペースなども対象になるところが異なります。

```java
String text = "\n\u3000 a \u3000\n";

assertThat(text.strip()).isEqualTo("a");
assertThat(text.stripLeading()).isEqualTo("a \u3000\n");
assertThat(text.stripTrailing()).isEqualTo("\n\u3000 a");

assertThat(text.trim()).isEqualTo("\u3000 a \u3000"); // 全角スペースは消えない
```

### ▲ String#isBlank の追加

`String#isBlank` で空白かどうか判定できます。

```java
assertThat(" ".isBlank()).isTrue();
assertThat("\n\t\u3000".isBlank()).isTrue();
```

### ▲ String#lines の追加

`String#lines` で行ごとの文字列の`Stream`を取得できるようになりました。

```java
String text = "a\nb\r\nc";
long lineCount = text.lines().count();

assertThat(lineCount).isEqualTo(3);
```

### ★ Path.of の追加

`Paths.get`だったのが、`Path.of` で書けるようになりました。他の作法と合わせたような感じです。

```java
Path path1 = Paths.get("dir");
Path path2 = Path.of("dir");

assertThat(path2).isEqualTo(path1);
```

### ★ Files.writeString、readString の追加

`Files.writeString`が追加され、1メソッドで文字の書き込みが行えるようになりました。  
同様に`readString`で1メソッドで読み込みが行えるようになりました。

```java
Files.writeString(filePath, "あいうえお", StandardCharsets.UTF_8);

String text = Files.readString(filePath, StandardCharsets.UTF_8);

assertThat(text).isEqualTo("あいうえお");
```

### ★ Predicate.not の追加

`Predicate.not`で`Predicate`を反転させられるようになりました。反転のためにメソッド参照を諦めていたような場所でメソッド参照が使えるようになります。

```java
long notEmptyCount = Stream.of("", "x", "", "x", "x")
        .filter(Predicate.not(String::isEmpty))
        .count();

assertThat(notEmptyCount).isEqualTo(3);
```

### ▲ Collection#toArray(IntFunction<T[]> generator) の追加

`Collection#toArray`で引数に配列をnewする関数を指定できるようになりました。  
以前は配列を生成して渡していた(`new String[0]`とか)のが、コンストラクタをメソッド参照で指定できるようになった感じです。

```java
List<String> list = List.of("a", "b", "c");
String[] array = list.toArray(String[]::new);
```

### ▲ Optional#isEmpty の追加

`Optional`に`isEmpty`が追加されました。`isPresent`の逆になります。

```java
Optional<String> value = Optional.ofNullable(null);

assertThat(value.isEmpty()).isTrue();
assertThat(value.isPresent()).isFalse();
```

## Java12

* https://openjdk.java.net/projects/jdk/12/

### ▲ String#indent の追加

`String`の`indent`メソッドで、指定した文字数分のインデントを付けられるようになりました。

```java
String base = "1\n2\n\n";
String indented = base.indent(2);

assertThat(indented).isEqualTo("  1\n  2\n  \n");
```

### ▲ String#transform の追加

`String`に`transform`メソッドが追加されました。  
`String`を引数に取るメソッドを渡すとそれを実行してその結果を返します。

```java
int num = "1".transform(Integer::parseInt);
```

メソッド呼び出し1回だと、あまりメリットを感じませんが、メソッド呼び出しが続くような場合には、読みやすくなります。

```java
int num = "12#34".transform(this::clean).transform(Integer::valueOf);
```

## Java13

* https://openjdk.java.net/projects/jdk/13/

## Java14

* https://openjdk.java.net/projects/jdk/14/

### ★ switch式

`switch`を式としても書けるようになりました。

```java
int numLetters = switch (day) {
    case MONDAY, FRIDAY, SUNDAY -> 6;
    case TUESDAY -> 7;
    case THURSDAY, SATURDAY -> 8;
    case WEDNESDAY -> 9;
    default -> throw new IllegalStateException("Invalid day: " + day);
};
```

上記はアロー構文を使った記述ですが、switch文同様のコロンラベル+`yield`で書く方法もあります。

```java
int numLetters = switch (day) {
    case MONDAY:
    case FRIDAY:
    case SUNDAY:
        System.out.println(6);
        yield 6;
    case TUESDAY:
        System.out.println(7);
        yield 7;
    case THURSDAY:
    case SATURDAY:
        System.out.println(8);
        yield 8;
    case WEDNESDAY:
        System.out.println(9);
        yield 9;
    default:
        throw new IllegalStateException("Invalid day: " + day);
};
```

アロー構文でも複数の式が必要な場合は、ブロック＋`yield`を使います。

```java
int numLetters = switch (day) {
    case MONDAY, FRIDAY, SUNDAY -> {
        System.out.println(6);
        yield 6;
    }
    case TUESDAY -> {
        System.out.println(7);
        yield 7;
    }
    case THURSDAY, SATURDAY -> {
        System.out.println(8);
        yield 8;
    }
    case WEDNESDAY -> {
        System.out.println(9);
        yield 9;
    }
    default -> throw new IllegalStateException("Invalid day: " + day);
};
```

アロー構文は`break`を忘れて意図せずフォールスルーすることが無いので、アロー構文を使った方がよさそうです。

* 参考: https://docs.oracle.com/javase/jp/13/language/switch-expressions.html

### ★ switchの複数ラベル

`switch`のラベルをカンマ区切りで複数書けるようになりました。  
`switch`式だけでなく、既存の`switch`文でも利用できます。

```java
switch (day) {
    case MONDAY, FRIDAY, SUNDAY:
        System.out.println(6);
        break;
    case TUESDAY:
        System.out.println(7);
        break;
    case THURSDAY, SATURDAY:
        System.out.println(8);
        break;
    case WEDNESDAY:
        System.out.println(9);
        break;
    default:
        throw new IllegalStateException("Invalid day: " + day);
}
```

## Java15

* https://openjdk.java.net/projects/jdk/15/

### ★ テキストブロック

複数行にまたがる文字列を宣言できます。`"""`で囲んで書きます。

```java
String text = """
        1行目
        2行目
        """;

assertThat(text)
        .isEqualTo("1行目\n2行目\n");
```

インデントは一番浅い位置が基準になります。

```java
String text = """
        1行目
          2行目(インデント)
        """;

assertThat(text)
        .isEqualTo("1行目\n  2行目(インデント)\n");
```

末尾のスペースは自動的に除去されます。 
スペースを付与したい場合には`\s`を利用します。末尾に付与すれば、それより前のスペースも含めて維持されます。

```java
String text = """
        1行目 \s
        2行目\s
        """;

assertThat(text)
        .isEqualTo("1行目  \n2行目 \n");
```

改行を付与したくない場合には、末尾に`\`を付与します。

```java
String text = """
        1行目\
        2行目\
        """;

assertThat(text)
        .isEqualTo("1行目2行目");
```

### ▲ String#formatted、stripIndent、translateEscapes

`String`クラスのインスタンスメソッドとして、`formatted`、`stripIndent`、`translateEscapes` が追加されました。  
テキストブロックに関連しての追加のようです。

`formatted`メソッドは、今まで`String.format`で呼び出していたものを、`String`のインスタンスメソッドとして呼び出せるようにしたものです。

```java
String formatText = "%,d円です。";
String text = formatText.formatted(1234);

assertThat(text).isEqualTo("1,234円です。");
```

テキストブロックでは変数埋め込みが出来ませんが、`formatted`メソッドを使うことで、下記のように書くことが出来ます。

```java
String text = """
        %sさん。
        金額は%,d円です。
        """.formatted("太郎", 1234);

assertThat(text).isEqualTo("太郎さん。\n金額は1,234円です。\n");
```

`stripIndent`は複数行の文字列から、一番浅いインデント分の空白を取り除きます。

```java
String text = " 1\n  2\n   3";

assertThat(text.stripIndent()).isEqualTo("1\n 2\n  3");
```

`translateEscapes`はエスケープシーケンスを変換します。  
たとえば、`\n`という2文字をU+000Aに変換してくれます。

```java
String text = "a\\n\\tb";

assertThat(text.translateEscapes()).isEqualTo("a\n\tb");
```

## Java16

* https://openjdk.java.net/projects/jdk/16/

### ★ パターンマッチングinstanceof

`instanceof` で型をチェックするのと同時に、その型の変数を定義できるようになりました。

```java
Object value = 10;

if (value instanceof Integer num) {
    assertThat(num).isEqualTo(10);
}
```

### ★ レコード

不変なデータを保持するクラスを簡単に定義できるようになりました。

`class`の代わりに`record`で定義します。  
名前の後に、フィールドのリスト(コンポーネントと呼ばれます)を記載します。

```java
public record Rectangle(int length, int width) {
}
```

`class`と同じく`new`でインスタンスを生成します。

```java
Rectangle rectangle = new Rectangle(1, 2);
```

`record`には、下記メソッドが自動的に定義されます。

* フィールド参照用のアクセサ
* `equals`
* `hashCode`
* `toString`

```java
assertThat(rectangle.length())
        .isEqualTo(1);
assertThat(rectangle.width())
        .isEqualTo(2);
assertThat(rectangle.toString())
        .isEqualTo("Rectangle[length=1, width=2]");

Rectangle other = new Rectangle(1, 2);
assertThat(rectangle.hashCode())
        .isEqualTo(other.hashCode());
assertThat(rectangle.equals(other))
        .isTrue();
```

独自メソッドも宣言できます。

```java
public record Rectangle(int length, int width) {

    public int area() {
        return length * width;
    }
}
```

* https://docs.oracle.com/javase/jp/16/language/records.html

### ★ Stream#toList

`Stream`に`toList`メソッドが追加されました。  
`collect(Collectos.toList())`で書いていた箇所が、もう少しすっきりとした形で書けるようになります。

```java
List<String> list = Stream.of("a", "b").toList();
```

`Stream`の`toList`は不変なリストを返します。そのため、そのリストに対して追加/削除/変更を行うと、`UnsuptorpedOperationException`が発生します。  
`Collectors.toList`で返すリストは、実装としては`ArrayList`になっているため、追加/削除/変更は出来ていました。そのため、`Collectors.toList`に置き換えると問題が出る箇所もありえるので注意が必要です。

なお、Java10で`Collectors.toUnmodifiableList`が追加されており、こちらも不変なリストを返すものになっています。



## 参考

* [Java新機能メモ\(Hishidama's Java up\-to\-date\)](https://www.ne.jp/asahi/hishidama/home/tech/java/uptodate.html)
* [開発者が喜ぶJDK 9の9つの新機能 - Oracle](https://www.oracle.com/webfolder/technetwork/jp/javamagazine/Java-JA17-Java9Features.pdf)
* [Java 9~17の新機能 / Java 9 ~ 17 Overview \- Speaker Deck](https://speakerdeck.com/maruta/java-9-17-overview?slide=2)
* [Javaのスペシャリストが教える、Java9からJava14で細かく変更された機能 \- ログミーTech](https://logmi.jp/tech/articles/323178)
