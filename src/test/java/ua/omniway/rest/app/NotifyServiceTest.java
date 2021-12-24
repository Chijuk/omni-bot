package ua.omniway.rest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.TestUtils.prepareRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:/conf/bot/omni-bot/bot-properties.xml")
@Transactional
@Sql("classpath:sql/users/insert.sql")
class NotifyServiceTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenMessageBodyEmpty_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/NotificationRequests/emptyMessage.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/approval-votes", request, String.class);

        assertThat(response.getBody()).contains("Field error in object 'approvalVoteNotification' on field 'messageBody': rejected value []");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    void whenMessageBodyNull_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/NotificationRequests/nullMessage.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/approval-votes/", request, String.class);

        assertThat(response.getBody()).contains("Field error in object 'approvalVoteNotification' on field 'messageBody': rejected value [null]");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }


    @Test
    void whenPersonIdIsUnknown_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/NotificationRequests/unknownUserUniqueId.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/approval-votes/", request, String.class);

        assertThat(response.getBody()).contains("is not DB user");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenEventNull_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/ApprovalVoteNotification/nullEvent.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/approval-votes/", request, String.class);

        assertThat(response.getBody()).contains("Field error in object 'approvalVoteNotification' on field 'event': rejected value [null]");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    void whenFeedbackOptionsEmpty_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/CustomerFeedbackNotification/emptyOptions.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/customer-feedback/", request, String.class);

        assertThat(response.getBody()).contains("Field error in object 'customerFeedbackNotification' on field 'options': rejected value [[]];");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    void whenFeedbackOptionsNull_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/CustomerFeedbackNotification/nullOptions.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/customer-feedback/", request, String.class);

        assertThat(response.getBody()).contains("Field error in object 'customerFeedbackNotification' on field 'options': rejected value [null];");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }

    @Test
    void whenFeedbackOptionNotValid_thenException() throws IOException {
        HttpEntity<String> request = prepareRequest("json/CustomerFeedbackNotification/emptyOption.json");

        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                "/notify/customer-feedback/", request, String.class);

        assertThat(response.getBody())
                .contains("Field error in object 'customerFeedbackNotification' on field 'options[0].callback': rejected value [null]")
                .contains("Field error in object 'customerFeedbackNotification' on field 'options[0].value': rejected value []");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }
}