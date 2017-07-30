package com.enjoyxstudy.lombok.example;

import java.util.ArrayList;

import lombok.val;

public class ValExample {

  public static void main(String[] args) {

    val list = new ArrayList<String>();
    list.add("a");
    list.add("b");

    val item = list.get(0);
    System.out.println(item);
  }
}
