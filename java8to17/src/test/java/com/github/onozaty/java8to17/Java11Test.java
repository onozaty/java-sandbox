package com.github.onozaty.java8to17;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class Java11Test {

    @Test
    public void string_repeat() {

        String text = "a".repeat(10);
        assertThat(text).isEqualTo("aaaaaaaaaa");

    }

    @Test
    public void string_strip() {

        String text = "\n\u3000 a \u3000\n";

        assertThat(text.strip()).isEqualTo("a");
        assertThat(text.stripLeading()).isEqualTo("a \u3000\n");
        assertThat(text.stripTrailing()).isEqualTo("\n\u3000 a");

        assertThat(text.trim()).isEqualTo("\u3000 a \u3000"); // 全角スペースは消えない
    }

    @Test
    public void string_isBlank() {

        assertThat(" ".isBlank()).isTrue();
        assertThat("\n\t\u3000".isBlank()).isTrue();
    }

    @Test
    public void path_of() {

        Path path1 = Paths.get("dir");
        Path path2 = Path.of("dir");

        assertThat(path2).isEqualTo(path1);
    }

    @Test
    public void writeString_readString() throws IOException {

        try (TempDirectory tempDirectory = new TempDirectory()) {

            Path filePath = tempDirectory.getPath().resolve("test.txt");

            Files.writeString(filePath, "あいうえお", StandardCharsets.UTF_8);

            String text = Files.readString(filePath, StandardCharsets.UTF_8);

            assertThat(text).isEqualTo("あいうえお");
        }
    }

}
