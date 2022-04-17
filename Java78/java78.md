Java7/8 を使うにあたって、知っておいた方がよいもの

* 高 (コードを書くときに頻繁に使用することになりそう)
  * **try-with-resources** [Java7]<br>
    リソースの有効範囲をブロックで指定して、解放漏れを防ぐ。C#でいうusing。
  * **More New I/O APIs for Java (NIO.2)** [Java7]<br>
    Path/PathsとFilesは覚えておくべき。ファイル、ディレクトリ操作が改善されている。
  * **lambda** [Java8]<br>
    関数をそのままオブジェクトとして渡せるように書ける。無名クラスのインスタンスを作るような書き方をしなくて済む。
  * **Stream API** [Java8]<br>
    リストなどに対して、関数型スタイルの操作を行えるように。C#でいうLinqに近い。
* 中 (知っとくと便利な時も)
  * **StandardCharsets** [Java7]<br>
    UTF-8を指定する際に、"UTF-8"と文字列を指定するのではなく、定数のStandardCharsets.UTF_8 が利用できる
  * **Catching Multiple Exception** [Java7]<br>
    catchで複数の例外を指定してまとめてcatchできる。
* 低 (そういったのがあること自体は頭の中に入れておく)
  * **switchでのString対応** [Java7]<br>
    switch文の指揮にStringが使えるようになった。
  * **Optional** [Java8]<br>
    値がないかもしれないことを表現するクラスとしてOptional<T>が追加。
  * **Date and Time API** [Java8]<br>
    日付、時間のAPIが大幅に改善/変更。
* その他 (参考程度)
  * **ダイアモンド演算子** [Java7]<br>
    ジェネリクスを使ったクラスをインスタンス化する際に、型引数を省略可能に





## 参考
* [Java SE 7の機能と拡張機能 (Oracle)](http://www.oracle.com/technetwork/jp/java/javase/jdk7-relnotes-418459-ja.html "Java SE 7の機能と拡張機能")
* [JDK 8の新機能 (Oracle)](http://www.oracle.com/technetwork/jp/java/javase/overview/8-whats-new-2157071-ja.html "JDK 8の新機能")
* [Java新機能メモ(Hishidama's Java up-to-date)](http://www.ne.jp/asahi/hishidama/home/tech/java/uptodate.html "Java新機能メモ(Hishidama's Java up-to-date)")
* [続・今日から始めるJava8 - JSR-310 Date and Time API - Taste of Tech Topics](http://acro-engineer.hatenablog.com/entry/20130213/1360691391 "続・今日から始めるJava8 - JSR-310 Date and Time API - Taste of Tech Topics")
