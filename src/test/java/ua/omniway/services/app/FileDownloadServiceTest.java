package ua.omniway.services.app;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Disabled
class FileDownloadServiceTest {
    private static File verySmall;
    private static File small;
    private static File medium;
    private static File large;
    private static File veryLarge;

    @BeforeAll
    static void beforeAll() {
        veryLarge = Paths.get("src/test/resources/test-files/15mb.txt").toFile();
    }

    @RepeatedTest(value = 10)
    void commonsFileToBytes() throws IOException {
        long start = System.currentTimeMillis();
        FileUtils.readFileToByteArray(veryLarge);
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) / 1000F + " seconds");
    }

    @RepeatedTest(value = 10)
    void javaFileToBytes() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        Files.readAllBytes(veryLarge.toPath());
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) / 1000F + " seconds");
    }

    @RepeatedTest(value = 10)
    void commonsBase64Benchamrk() throws IOException {
        long start = System.currentTimeMillis();
        org.apache.commons.codec.binary.Base64.encodeBase64String(FileUtils.readFileToByteArray(veryLarge));
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) / 1000F + " seconds");
    }

    @RepeatedTest(value = 10)
    void javaBase64Benchmark() throws IOException {
        long start = System.currentTimeMillis();
        java.util.Base64.getEncoder().encodeToString(Files.readAllBytes(veryLarge.toPath()));
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start) / 1000F + " seconds");
    }
}