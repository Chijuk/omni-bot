package ua.omniway.dao.ot;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.models.ot.ApprovalRequest;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class ApprovalRequestsDaoTest {

    @Autowired
    private ApprovalRequestsDao approvalRequestsDao;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @ParameterizedTest
    @MethodSource("ua.omniway.dao.ot.SoapTestingUtils#provideTestApprovalRequestObjects")
    void whenParseFind_thenSuccess(ApprovalRequest expected) throws OmnitrackerException {
        insertStub(GET_OBJECT_LIST, "100001", "ApprovalsApiServiceTest/getRequestsList.xml");

        ApprovalRequest actual = approvalRequestsDao.findAll(List.of(100001L)).stream()
                .filter(expected::equals).findFirst().get();

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


}