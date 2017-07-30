package com.enjoyxstudy.lombok.example;

import lombok.ToString;

@ToString
public class ToStringExample {

  private int id;

  private String name;

  public String getName() {
    return name;
  }
}

// excludeで除外するフィールドを指定
@ToString(exclude = "name")
class ToStringExample2 {

  private int id;

  private String name;

  public String getName() {
    return name;
  }
}
