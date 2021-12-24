package ua.omniway.services.app;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.SubscribersApi;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class SubscribersApiServiceTest {

    @Autowired
    private SubscribersApi apiService;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @Test
    void whenAuthorized_thenReturnId() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "good", "SubscribersApiServiceTest/authorizeWithPasswordReturnId.xml");

        assertThat(apiService.authorizeWithPassword("good")).isEqualTo(100L);
    }

    @Test
    void whenNotAuthorized_thenReturnNull() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "wrong", "SubscribersApiServiceTest/authorizeWithPasswordReturnNull.xml");

        assertThat(apiService.authorizeWithPassword("wrong")).isNull();
    }
}