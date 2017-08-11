package com.enjoyxstudy.java78;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Nio2Example {

  // BufferedReaderの生成方法
  // ※BufferedWriterも同じ

  public String readFirstLineFromFile(String path) throws IOException {

    try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
      return reader.readLine();
    }
  }

  public String readFirstLineFromFileOld(String path) throws IOException {

    BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

    try {
      return reader.readLine();
    } finally {
      reader.close();
    }
  }

  // コピー

  public void copy(String fromFile, String toPath) throws IOException {

    Files.copy(Paths.get(fromFile), Paths.get(toPath));
  }

  public void copyOld(String fromPath, String toFile) throws IOException {

    FileInputStream inputStream = new FileInputStream(fromPath);
    try {

      FileOutputStream outputStream = new FileOutputStream(toFile);
      try {

        byte[] buffer = new byte[1024];

        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, length);
        }

      } finally {
        outputStream.close();
      }

    } finally {
      inputStream.close();
    }
  }

  // 内容取得

  public byte[] readAllBytes(String path) throws IOException {

    return Files.readAllBytes(Paths.get(path));
  }

  public byte[] readAllBytesOld(String path) throws IOException {

    FileInputStream inputStream = new FileInputStream(path);
    try {

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      try {

        byte[] buffer = new byte[1024];

        int length;
        while ((length = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, length);
        }

        return outputStream.toByteArray();

      } finally {
        outputStream.close();
      }

    } finally {
      inputStream.close();
    }
  }
}
