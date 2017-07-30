package com.enjoyxstudy.lombok.example;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class EqualsAndHashCodeExample {

  private int id;

  private String name;
}

@EqualsAndHashCode(exclude = "age")
class EqualsAndHashCodeExample2 {

  private int id;

  private String name;

  private int age;
}
