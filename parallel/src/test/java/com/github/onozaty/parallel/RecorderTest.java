package com.github.onozaty.parallel;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class RecorderTest {

    @Test
    public void test() throws IOException {
        Recorder recorder = new Recorder();

        int event1 = recorder.start();
        int event2 = recorder.start();
        int event3 = recorder.start();
        recorder.end(event2);
        int event4 = recorder.start();
        recorder.end(event1);
        recorder.end(event4);
        recorder.end(event3);

        {
            StringBuilder builder = new StringBuilder();
            recorder.printByRecordingId(builder);

            assertThat(builder.toString())
                    .isEqualTo("""
                    1 : ++++++ \s
                    2 :  +++   \s
                    3 :   ++++++
                    4 :     +++\s
                    """);
        }
        {
            StringBuilder builder = new StringBuilder();
            recorder.printByActiveCount(builder);

            assertThat(builder.toString())
                    .isEqualTo("""
                    3 :   ++++ \s
                    2 :  ++++++\s
                    1 : ++++++++
                    """);
        }
    }

}
