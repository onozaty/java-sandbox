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

public class UserGroupTest {

    @Test
    public void test() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        UserGroup userGroupBefore = new UserGroup("group1", Arrays.asList(new User(1, "Taro", null)));

        String json = mapper.writeValueAsString(userGroupBefore);
        UserGroup userGroupAfter = mapper.readValue(json, UserGroup.class);

        assertEquals(userGroupBefore, userGroupAfter);
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
}
