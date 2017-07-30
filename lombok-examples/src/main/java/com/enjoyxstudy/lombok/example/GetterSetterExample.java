package com.enjoyxstudy.lombok.example;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class GetterSetterExample {

  // フィールドに対して指定
  @Getter
  @Setter
  private int id;

  // アクセスレベルを指定
  @Setter(AccessLevel.PROTECTED)
  private String name;
}

// クラスに対してまとめて指定もできる
@Getter
class GetterSetterExample2 {

  private int id;

  private String name;
}
