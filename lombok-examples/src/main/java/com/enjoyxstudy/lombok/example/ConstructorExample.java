package com.enjoyxstudy.lombok.example;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class ConstructorExample {

  private final int id;

  private String name;
}

@NoArgsConstructor
class ConstructorExample2 {

  private int id;

  private String name;
}
