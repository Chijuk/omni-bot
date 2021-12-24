package ua.omniway.dao.ot;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.sun.xml.ws.client.ClientTransportException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.models.ot.ProcessData;

import javax.xml.ws.WebServiceException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@Slf4j
@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class ProcessDataDaoTest {

    @Autowired
    private ProcessDataDao processDataDao;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @ParameterizedTest
    @MethodSource("ua.omniway.dao.ot.SoapTestingUtils#provideTestProcessDataObjects")
    void whenParseFind_thenSuccess(ProcessData expected) throws OmnitrackerException {
        insertStub(GET_OBJECT_LIST, "100001", "ProcessDataDaoTest/getRequestsList.xml");

        ProcessData actual = processDataDao.findAll(List.of(100001L)).stream()
                .filter(expected::equals).findFirst().get();

        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    void whenParseFind_cyrillicIsOk() throws OmnitrackerException {
        insertStub(GET_OBJECT_LIST, "99376075", "ProcessDataDaoTest/getRequestsListCyrillic.xml");

        ProcessData actual = processDataDao.findAll(List.of(99376075L)).stream().findFirst().get();

        assertThat(actual.getDescription()).containsIgnoringCase("Необходима поверка по весам:");
    }

    @Test
    void whenSendRequest_thenTimeout() {
        stubFor(post(urlMatching(MATCHING_URL))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(GET_OBJECT_LIST))
                .withRequestBody(containing("666"))
                .willReturn(aResponse().withFixedDelay(30000))
        );

        assertThatExceptionOfType(WebServiceException.class).isThrownBy(() -> processDataDao.findById(666L));
    }

    @Test
    void whenSendRequest_thenServerException() {
        stubFor(post(urlMatching(MATCHING_URL))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(GET_OBJECT_LIST))
                .withRequestBody(containing("500"))
                .willReturn(serverError())
        );

        assertThatExceptionOfType(ClientTransportException.class)
                .isThrownBy(() -> processDataDao.findById(500L))
                .withMessageContaining("The server sent HTTP status code 500: Server Error");
    }
}