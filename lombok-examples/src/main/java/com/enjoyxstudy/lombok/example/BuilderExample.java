package com.enjoyxstudy.lombok.example;

import java.util.List;

import lombok.Builder;
import lombok.Singular;
import lombok.ToString;

public class BuilderExample {

  public static void main(String[] args) {

    Customer customer = Customer.builder()
        .id(1)
        .name("Taro")
        .tag("A")
        .tag("B")
        .build();

    System.out.println(customer);
    // -> Customer(id=1, name=Taro, tags=[A, B])
  }
}

@Builder
@ToString
class Customer {

  private int id;

  private String name;

  @Singular
  private List<String> tags;
}