package com.github.onozaty.parallel;

import java.util.concurrent.TimeUnit;

public class HeavyObject {

    public HeavyObject() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void execute(int i) {
    }
}
