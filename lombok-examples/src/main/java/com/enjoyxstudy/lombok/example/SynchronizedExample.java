package com.enjoyxstudy.lombok.example;

import lombok.Synchronized;

public class SynchronizedExample {

  @Synchronized
  public static void hello() {
    System.out.println("test");
  }

  @Synchronized
  public void bye() {
    System.out.println("bye");
  }
}
