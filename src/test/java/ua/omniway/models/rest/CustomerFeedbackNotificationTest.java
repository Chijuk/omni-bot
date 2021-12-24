package ua.omniway.models.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ua.omniway.TestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerFeedbackNotificationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void whenNotValidOption_thenException() throws IOException {
        String json = TestUtils.readResource("json/CustomerFeedbackNotification/emptyOption.json");

        CustomerFeedbackNotification notification = mapper.readValue(json, CustomerFeedbackNotification.class);

        assertThat(notification.getOptions().size()).isEqualTo(2);
    }

}