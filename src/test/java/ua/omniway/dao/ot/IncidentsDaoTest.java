package ua.omniway.dao.ot;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.DaoException;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.models.ot.Incident;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@Slf4j
@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class IncidentsDaoTest {

    @Autowired
    private IncidentsDao incidentsDao;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @Test
    void whenGetObjectsForCaller_thenSuccess() throws DaoException {
        insertStub(GET_OBJECT_LIST, "99376833", "IncidentsDaoTest/getRequestsForCallerList.xml");

        final List<Incident> changes = incidentsDao.getRequestsForCaller(99376833);

        assertThat(changes.size()).isEqualTo(3);
    }

    @Test
    void whenGetObjectsForCaller_thenEmptyList() throws DaoException {
        insertStub(GET_OBJECT_LIST, "0", "getRequestsEmpty.xml");

        final List<Incident> changes = incidentsDao.getRequestsForCaller(0);

        assertThat(changes).isEmpty();
    }

    @Test
    void whenGetObjectsForCaller_thenOmnitrackerException() {
        insertStub(GET_OBJECT_LIST, "404", "getRequestsError.xml");

        assertThatExceptionOfType(DaoException.class)
                .isThrownBy(() -> incidentsDao.getRequestsForCaller(404))
                .withCauseInstanceOf(OmnitrackerException.class)
                .withMessageEndingWith("Error message from omnitracker");
    }
}