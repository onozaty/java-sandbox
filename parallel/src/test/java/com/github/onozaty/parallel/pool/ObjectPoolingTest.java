package com.github.onozaty.parallel.pool;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.Test;

import com.github.onozaty.parallel.HeavyObject;

public class ObjectPoolingTest {

    @Test
    public void none() {

        IntStream.range(0, 200).parallel()
                .forEach(i -> {

                    HeavyObject heavyObject = new HeavyObject();
                    heavyObject.execute(i);
                });
    }

    @Test
    public void poolingByQueue() {

        // 事前に同時に利用される数分オブジェクトを作っておく
        int size = ForkJoinPool.getCommonPoolParallelism() + 1;
        ConcurrentLinkedQueue<HeavyObject> objectQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < size; i++) {
            objectQueue.add(new HeavyObject());
        }

        IntStream.range(0, 200).parallel()
                .forEach(i -> {

                    HeavyObject heavyObject = objectQueue.poll();
                    try {
                        heavyObject.execute(i);
                    } finally {
                        objectQueue.add(heavyObject);
                    }
                });
    }

    @Test
    public void poolingByThreadLocal() {

        // スレッド毎に別のオブジェクトを管理
        ThreadLocal<HeavyObject> threadLocal = new ThreadLocal<>();

        IntStream.range(0, 200).parallel()
                .forEach(i -> {

                    HeavyObject heavyObject = threadLocal.get();
                    if (heavyObject == null) {
                        heavyObject = new HeavyObject();
                        threadLocal.set(heavyObject);
                    }

                    heavyObject.execute(i);
                });
    }

    @Test
    public void poolingByCommonsPool() {

        int size = ForkJoinPool.getCommonPoolParallelism() + 1;

        try (GenericObjectPool<HeavyObject> objectPool = new GenericObjectPool<>(new HeavyObjectFactory())) {

            objectPool.setMaxTotal(size);

            IntStream.range(0, 200).parallel()
                    .forEach(i -> {

                        HeavyObject heavyObject = null;
                        try {
                            heavyObject = objectPool.borrowObject();
                            heavyObject.execute(i);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        } finally {
                            objectPool.returnObject(heavyObject);
                        }
                    });
        }
    }

}
