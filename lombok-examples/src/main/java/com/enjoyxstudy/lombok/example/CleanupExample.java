package com.enjoyxstudy.lombok.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.Cleanup;

public class CleanupExample {

  public static void main(String[] args) throws IOException {

    Path filePath = Paths.get(args[0]);
    @Cleanup BufferedReader reader = Files.newBufferedReader(filePath);

    System.out.println(reader.readLine());
  }
}
