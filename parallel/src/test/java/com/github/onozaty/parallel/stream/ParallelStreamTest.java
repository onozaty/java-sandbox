package com.github.onozaty.parallel.stream;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import com.github.onozaty.parallel.Recorder;

public class ParallelStreamTest {

    @Test
    public void parallelStream_default() throws IOException {

        Random random = new Random();
        Recorder recorder = new Recorder();

        System.out.println("ForkJoinPool.getCommonPoolParallelism :" + ForkJoinPool.commonPool().getParallelism());

        IntStream.range(0, 30).parallel()
                .forEach(i -> {

                    System.out.println(Thread.currentThread().getName());

                    int recordingId = recorder.start();

                    try {
                        // 3秒内でランダムな時間待ち合わせ
                        Thread.sleep(random.nextInt(3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    recorder.end(recordingId);
                });

        System.out.println();

        System.out.println("By Redcording");
        recorder.printByRecordingId(System.out);

        System.out.println();

        System.out.println("Active Count Graph");
        recorder.printByActiveCount(System.out);
    }

    @Test
    public void parallelStream_custom() throws IOException, InterruptedException, ExecutionException {

        Random random = new Random();
        Recorder recorder = new Recorder();

        System.out.println("ForkJoinPool.getCommonPoolParallelism :" + ForkJoinPool.commonPool().getParallelism());

        ForkJoinPool customThreadPool = new ForkJoinPool(3);
        try {
            customThreadPool.submit(
                    () -> {
                        IntStream.range(0, 30).parallel()
                                .forEach(i -> {

                                    System.out.println(Thread.currentThread().getName());

                                    int recordingId = recorder.start();

                                    try {
                                        // 3秒内でランダムな時間待ち合わせ
                                        Thread.sleep(random.nextInt(3000));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    recorder.end(recordingId);
                                });
                    }).get();

        } finally {
            customThreadPool.shutdown();
        }

        System.out.println();

        System.out.println("By Redcording");
        recorder.printByRecordingId(System.out);

        System.out.println();

        System.out.println("Active Count Graph");
        recorder.printByActiveCount(System.out);
    }
}
