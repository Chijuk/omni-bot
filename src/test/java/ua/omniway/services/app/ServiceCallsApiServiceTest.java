package ua.omniway.services.app;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.services.exceptions.ServiceException;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class ServiceCallsApiServiceTest {

    @Autowired
    private ServiceCallsApiService apiService;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @Test
    void whenCreateGeneralWihAtt_thenSuccess() throws ServiceException, OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "some subject", "ServiceCallsApiServiceTest/createGeneralSuccess.xml");
        List<AttachmentMeta> attachmentsList = provideTestAttachmentsList();

        long actual = apiService.createGeneral(412L, "some subject", null, attachmentsList);

        assertThat(actual).isEqualTo(489065);
    }
}