package com.enjoyxstudy.lombok.example;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class NonNullExample {

  private String name;

  public NonNullExample(@NonNull String name) {
    this.name = name;
  }

  public void printLength(@NonNull String message) {
    System.out.println(message.length());
  }
}

@RequiredArgsConstructor
@Setter
class NonNullExample2 {

  @NonNull
  private String name;
}
