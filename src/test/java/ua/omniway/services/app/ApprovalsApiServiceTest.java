package ua.omniway.services.app;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.OmnitrackerException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class ApprovalsApiServiceTest {

    @Autowired
    private ApprovalsApiService apiService;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @Test
    void whenExecuteCanSetResult_thenSuccess() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "1001", "ApprovalsApiServiceTest/canSetResult.xml");

        assertThat(apiService.canSetResult(1001L, 1001L)).isTrue();
    }
}