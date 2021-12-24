package ua.omniway.bot.telegram.handlers.attachments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
class GeneralFileMessageHandlerTest {
    @Autowired
    private GeneralFileMessageHandler fileMessageHandler;
    @Autowired(required = false)
    private List<AttachmentListener> listeners;

    @Test
    void whenInit_thenListenersNotNull() {
        assertThat(fileMessageHandler).isNotNull();
        assertThat(fileMessageHandler.getListeners()).isNotNull();
    }
}