# try-with-resources - Java7

try文で指定したリソースを、ブロックを抜けた際にクローズする。ブロックとリソースがセットになるので、コードがわかりやすくなる。

C#のusingブロックと同じ感じ。

```java
public String readFirstLineFromFile(String path) throws IOException {

  // AutoCloseableを実装しているクラスだとtry-with-resourcesで書ける
  try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
    return reader.readLine();
  }
}
```

Java7より前だと、try/finallyで書くので煩雑。

```Java
public String readFirstLineFromFileOld(String path) throws IOException {

  BufferedReader reader = new BufferedReader(new FileReader(path));

  try {
    return reader.readLine();
  } finally {
    reader.close();
  }
}
```

また、上記例だとtry句内のreader.readLineで例外が発生し、finally句のreader.closeでも例外が発生した場合、このメソッドからスローされる例外はfinally句のreader.closeで発生した例外となってしまい、reader.readLineで発生した例外内容がわからない。

Java7のtry-with-resourcesだと、try句内で発生した例外が優先され、リソース解放時の例外は抑制された例外として、try句内の例外に設定される。これはThrowable.getSuppressedで取り出すことができる。
また、例外メッセージにも抑止された例外の内容も含まれるので、どちらのエラー内容もわかるのでエラーを確認しやすい。

```
Exception in thread "main" java.lang.Exception: Something happened
 at Foo.bar(Foo.java:10)
 at Foo.main(Foo.java:5)
 Suppressed: Resource$CloseFailException: Resource ID = 0
         at Resource.close(Resource.java:26)
         at Foo.bar(Foo.java:9)
         ... 1 more
```

複数のリソースを対象とする場合には、セミコロン区切りで。

```java
public static void writeToFileZipFileContents(String zipFileName, String outputFileName)
    throws IOException {

  Path outputFilePath = Paths.get(outputFileName);

  // 2つ以上のリソースを対象とする場合はセミコロンで
  try (
      ZipFile zipFile = new ZipFile(zipFileName);
      BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {

    // 処理....
  }
}
```

## 参考

* [ORACLE Java SE Documentation - try-with-resources 文](http://docs.oracle.com/javase/jp/7/technotes/guides/language/try-with-resources.html#suppressed-exceptions "try-with-resources 文")
