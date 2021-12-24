package ua.omniway.client.ot.soap.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ua.omniway.dao.exceptions.OmnitrackerException;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static ua.omniway.dao.ot.SoapTestingUtils.*;

@Slf4j
@SpringBootTest
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@WireMockTest(httpPort = 8083)
class CommonApiServiceTest {

    @Autowired
    private CommonApiService apiService;

    @AfterEach
    void tearDown() {
        verify(1, postRequestedFor(urlMatching(MATCHING_URL)));
    }

    @Test
    void whenExecute_thenReturnString() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "string", "CommonsApiServiceTest/invokeScriptReturnString.xml");

        final String actual = (String) apiService.execute("script", "string");
        assertThat(actual).isInstanceOf(String.class).isEqualTo("OK");
    }

    @Test
    void whenExecute_thenReturnSBoolean() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "boolean", "CommonsApiServiceTest/invokeScriptReturnBoolean.xml");

        Boolean actual = (Boolean) apiService.execute("script", "boolean");
        assertThat(actual).isInstanceOf(Boolean.class).isTrue();
    }

    @Test
    void whenExecute_thenReturnLong() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "long", "CommonsApiServiceTest/invokeScriptReturnLong.xml");

        final Long actual = (Long) apiService.execute("script", "long");
        assertThat(actual).isInstanceOf(Long.class).isEqualTo(4100456L);
    }

    @Test
    void whenExecute_thenReturnLocalDateTime() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "date", "CommonsApiServiceTest/invokeScriptReturnLocalDateTime.xml");

//        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        LocalDateTime localDateTime = LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER);

        final LocalDateTime actual = (LocalDateTime) apiService.execute("script", "date");
        assertThat(actual).isInstanceOf(LocalDateTime.class).isEqualTo("2021-10-20T16:13:48");
    }

    @Test
    void whenExecute_thenReturnNull() throws OmnitrackerException {
        insertStub(INVOKE_SCRIPT, "null", "CommonsApiServiceTest/invokeScriptReturnNull.xml");

        assertThat(apiService.execute("script", "null")).isNull();
    }

    @Test
    void whenExecute_thanException() {
        insertStub(INVOKE_SCRIPT, "error", "CommonsApiServiceTest/invokeScriptError.xml");

        assertThatExceptionOfType(OmnitrackerException.class)
                .isThrownBy(() -> apiService.execute("script", "error"))
                .withMessageContaining("Some error message from OMNITRACKER");
    }
}