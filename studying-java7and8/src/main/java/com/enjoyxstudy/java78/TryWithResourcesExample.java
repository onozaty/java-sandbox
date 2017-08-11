package com.enjoyxstudy.java78;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

/**
 * http://docs.oracle.com/javase/jp/7/technotes/guides/language/try-with-resources.html
 *
 */
public class TryWithResourcesExample {

  public String readFirstLineFromFile(String path) throws IOException {

    // AutoCloseableを実装しているクラスだとtry-with-resourcesで書ける
    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      return reader.readLine();
    }
  }

  public String readFirstLineFromFileOld(String path) throws IOException {

    BufferedReader reader = new BufferedReader(new FileReader(path));

    try {
      return reader.readLine();
    } finally {
      reader.close();
    }
  }

  public static void writeToFileZipFileContents(String zipFileName, String outputFileName)
      throws IOException {

    Path outputFilePath = Paths.get(outputFileName);

    // 2つ以上のリソースを対象とする場合はカンマで
    try (
        ZipFile zipFile = new ZipFile(zipFileName);
        BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {

      // 処理....
    }
  }
}
