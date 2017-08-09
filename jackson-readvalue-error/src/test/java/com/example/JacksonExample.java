package com.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Jackson 2.8.7で発生するエラーの検証コードです。
 * 
 */
public class JacksonExample {

  @Test
  public void test1() throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    User userBefore = new User(1, "Taro", "password");

    String json = mapper.writeValueAsString(userBefore);
    User userAfter = mapper.readValue(json, User.class);
  }

  @Test
  public void test2() throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    User2 userBefore = new User2(1, "Taro", null);

    String json = mapper.writeValueAsString(userBefore);

    // 引数無しコンストラクタを使わせればエラーとならない
    User2 userAfter = mapper.readValue(json, User2.class);

    assertEquals(userBefore, userAfter);
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class User {

    private int id;

    private String name;

    @JsonIgnore
    private String password;
  }

  @Data
  @AllArgsConstructor(onConstructor = @__(@JsonIgnore))
  @NoArgsConstructor
  public static class User2 {

    private int id;

    private String name;

    @JsonIgnore
    private String password;
  }
}
