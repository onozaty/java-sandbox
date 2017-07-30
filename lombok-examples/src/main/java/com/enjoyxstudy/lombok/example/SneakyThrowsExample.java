package com.enjoyxstudy.lombok.example;

import lombok.SneakyThrows;

public class SneakyThrowsExample {

  @SneakyThrows
  public static void main(String[] args) {
    throw new Exception();
  }
}
