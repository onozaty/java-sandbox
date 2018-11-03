package com.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.SneakyThrows;

public class Process {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void executeWithFuture() throws InterruptedException, ExecutionException {

        System.out.println("[executeWithFuture] start");

        Future<?> future1 = executorService.submit(this::task1);
        Future<?> future2 = executorService.submit(this::task2);

        waitAll(future1, future2);

        task3();

        System.out.println("[executeWithFuture] end");
    }

    private void waitAll(Future<?>... futures) throws InterruptedException, ExecutionException {
        for (Future<?> future : futures) {
            future.get();
        }
    }

    public void executeWithCountDownLatch() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        
        System.out.println("[executeWithCountDownLatch] start");

        executorService.submit(() -> {
            task1();
            countDownLatch.countDown();
        });
        executorService.submit(() -> {
            task2();
            countDownLatch.countDown();
        });

        countDownLatch.await();
        
        task3();

        System.out.println("[executeWithCountDownLatch] end");
    }

    private void task1() {
        System.out.println("[task1] start");

        sleepRandom();

        System.out.println("[task1] end");
    }

    private void task2() {
        System.out.println("[task2] start");

        sleepRandom();

        System.out.println("[task2] end");
    }

    private void task3() {
        System.out.println("[task3] start");

        sleepRandom();

        System.out.println("[task3] end");
    }

    @SneakyThrows
    private void sleepRandom() {
        // ランダム(0から1秒の間)に停止
        Thread.sleep(new Random().nextInt(1000));
    }
}
