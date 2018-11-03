package com.example;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class ProcessTest {

    @Test
    public void executeWithFuture() throws InterruptedException, ExecutionException {
        new Process().executeWithFuture();
    }

    @Test
    public void executeWithCountDownLatch() throws InterruptedException {
        new Process().executeWithCountDownLatch();
    }
}
