package ua.omniway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileInputStream;
import java.io.IOException;

public class TestUtils {

    public static String readResource(final String path) throws IOException {
        return Resources.toString(Resources.getResource(path), Charsets.UTF_8);
    }

    public static HttpEntity<String> prepareRequest(final String path) throws IOException {
        return prepareRequest(path, MediaType.APPLICATION_JSON);
    }

    public static HttpEntity<String> prepareRequest(final String path, MediaType mediaType) throws IOException {
        String body = TestUtils.readResource(path);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        return new HttpEntity<>(body, headers);
    }

    @SneakyThrows
    public static Message readMessage(String path) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new FileInputStream(path), Message.class);
    }
}
