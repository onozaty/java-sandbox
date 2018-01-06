package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private Map<Integer, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getId(), user);
    }

    public User find(int id) {
        return users.get(id);
    }

    public List<User> findAll() {
        return new ArrayList<User>(users.values());
    }
}
