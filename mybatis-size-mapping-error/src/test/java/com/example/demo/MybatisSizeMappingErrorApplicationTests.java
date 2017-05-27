package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisSizeMappingErrorApplicationTests {

    @Autowired
    private TestRepository repository;

    @Test
    public void test() {

        repository.insert(1, 2);
    }
}
