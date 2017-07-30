package com.example;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
public class UserGroupTest {

  @Test
  public void test1() throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    User userBefore = new User(1, "Taro", null);

    String json = mapper.writeValueAsString(userBefore);
    User userAfter = mapper.readValue(json, User.class);

    assertEquals(userBefore, userAfter);
  }

  @Test
  public void test2() throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    UserGroup userGroupBefore = new UserGroup("group1", Arrays.asList(new User(1, "Taro", null)));

    String json = mapper.writeValueAsString(userGroupBefore);
    UserGroup userGroupAfter = mapper.readValue(json, UserGroup.class);

    assertEquals(userGroupBefore, userGroupAfter);
  }

  @Test
  public void test3() throws IOException {

    ObjectMapper mapper = new ObjectMapper();

    User2 userBefore = new User2(1, "Taro", null);

    String json = mapper.writeValueAsString(userBefore);

    // 引数無しコンストラクタを使わせればエラーとならない
    User2 userAfter = mapper.readValue(json, User2.class);

    assertEquals(userBefore, userAfter);
  }

  @Data
  @AllArgsConstructor
  public static class UserGroup {

    private String name;

    private List<User> users;
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
