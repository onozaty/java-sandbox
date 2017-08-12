# NIO2 - Java7

ファイル操作関連でいろいろ便利になっている。

## Files, Path

Filesのstaticメソッドを通して様々な機能が提供されている。また、Filesに対するパスの情報はPathクラスを使用するようになっている。Java7より前はFileがその役目だったが、NIO2ではPathに統一されている。

PathはFileSystemから取得する。
```java
// logs/access.log
Path path = FileSystems.getDefault().getPath("logs", "access.log");
```

Paths#getを使うと、FileSystems.getDefault().getPath と同等になるので、よく使うのはこちら。
```java
Path path = Path.get("logs", "access.log");
```

パスの組み立ても、Pathクラスで行える。

```Java
Path currentPath = Paths.get("/home/user1");

// 親のパス
System.out.println(currentPath.getParent());      // -> /home

// パスの結合
System.out.println(currentPath.resolve("1.txt")); // -> /home/user1/1.txt
```



## 参考

* [Javaファイル関連メモ2(Hishidama's Java Files Memo)](http://www.ne.jp/asahi/hishidama/home/tech/java/files.html "Javaファイル関連メモ2(Hishidama's Java Files Memo)")
