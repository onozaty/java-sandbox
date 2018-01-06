package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import org.junit.Test;

public class UserRepositoryTest {

    @Test
    public void save() {

        UserRepository repository = new UserRepository();
        repository.save(new User(1, "Yamada Hanako"));
        repository.save(new User(1, "Tanaka Hanako"));

        assertThat(repository.findAll())
                .hasSize(1)
                .first()
                .returns(1, User::getId)
                .returns("Tanaka Hanako", User::getName);
    }

    @Test
    public void find() {

        UserRepository repository = new UserRepository();
        repository.save(new User(1, "Yamada Hanako"));
        repository.save(new User(2, "Yamada Taro"));
        repository.save(new User(3, "Tanaka Jiro"));

        assertThat(repository.find(2))
                .returns(2, User::getId)
                .returns("Yamada Taro", User::getName);
    }

    @Test
    public void findAll() {

        UserRepository repository = new UserRepository();
        repository.save(new User(1, "Yamada Hanako"));
        repository.save(new User(2, "Yamada Taro"));
        repository.save(new User(3, "Tanaka Jiro"));

        assertThat(repository.findAll())
                .hasSize(3)
                .extracting(User::getId, User::getName)
                .containsExactlyInAnyOrder(
                        tuple(1, "Yamada Hanako"),
                        tuple(2, "Yamada Taro"),
                        tuple(3, "Tanaka Jiro"));
    }

}
