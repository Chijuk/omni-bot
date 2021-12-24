package ua.omniway.dao.ot;

import org.springframework.http.HttpHeaders;
import ua.omniway.models.ot.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * Test purposes only
 */
public class SoapTestingUtils {
    public static final String MATCHING_URL = "/OtWs/v1.asmx";
    public static final String GET_OBJECT_LIST = "http://www.omninet.de/OtWebSvc/v1/GetObjectList";
    public static final String INVOKE_SCRIPT = "http://www.omninet.de/OtWebSvc/v1/InvokeScript";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static void insertStub(String soapAction, String containing, String bodyFile) {
        stubFor(post(urlMatching(MATCHING_URL))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing(soapAction))
                .withRequestBody(containing(containing))
                .willReturn(ok().withBodyFile(bodyFile))
        );
    }

    public static List<ProcessData> provideTestProcessDataObjects() {
        return Stream.of(
                        (ProcessData) new ProcessData().setId(101L).setSummary("new change").setState("Assigned")
                                .setObjectType(ObjectType.CHANGE_REQUEST).setCaller(new Contact(100L))
                                .setUniqueId(100001),
                        (ProcessData) new ProcessData().setId(102L).setSummary("new incident").setState("Work")
                                .setObjectType(ObjectType.INCIDENT).setCaller(new Contact(101L))
                                .setDescription("new incident description")
                                .setDeadline(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setDateClosed(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setUniqueId(100002),
                        (ProcessData) new ProcessData().setId(103L).setSummary("new service request").setState("Work")
                                .setObjectType(ObjectType.SERVICE_REQUEST).setCaller(new Contact(101L))
                                .setDescription("new service request description")
                                .setDeadline(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setDateClosed(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setUniqueId(100003))
                .collect(Collectors.toList());
    }

    public static List<ApprovalRequest> provideTestApprovalRequestObjects() {
        return Stream.of(
                        (ApprovalRequest) new ApprovalRequest().setApprovalResult(ApprovalResultEnum.NOT_VOTED)
                                .setId(101L)
                                .setSummary("APPR-0001138431 - Заявка на створення віртуальної робочої станції")
                                .setState("ApproveToClosed").setObjectType(ObjectType.APPROVAL_REQUEST)
                                .setDeadline(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setCaller(new Contact(3054493L)).setDescription("TEST")
                                .setDateClosed(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setUniqueId(100001),
                        (ApprovalRequest) new ApprovalRequest().setApprovalResult(ApprovalResultEnum.APPROVED)
                                .setDateApproved(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setId(102L)
                                .setSummary("APPR-0001138432 - Заявка на створення віртуальної робочої станції")
                                .setState("ApproveToClosed").setObjectType(ObjectType.APPROVAL_REQUEST)
                                .setDeadline(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setCaller(new Contact(3054493L))
                                .setDateClosed(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setUniqueId(100002),
                        (ApprovalRequest) new ApprovalRequest().setApprovalResult(ApprovalResultEnum.REJECTED)
                                .setDateApproved(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setComment("Comment on NO result")
                                .setId(103L)
                                .setSummary("APPR-0001138433 - Заявка на створення віртуальної робочої станції")
                                .setState("ApproveToClosed").setObjectType(ObjectType.APPROVAL_REQUEST)
                                .setDeadline(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setCaller(new Contact(3054493L)).setDescription("TEST")
                                .setDateClosed(LocalDateTime.parse("2021-10-20T16:13:48", FORMATTER))
                                .setUniqueId(100003))
                .collect(Collectors.toList());
    }

    public static List<AttachmentMeta> provideTestAttachmentsList() {
        List<AttachmentMeta> attachments = new ArrayList<>();
        AttachmentMeta attachment1 = new AttachmentMeta();
        attachment1.setOid("ec06ec6e-8a79-424d-a9ec-a432edad14e0");
        attachment1.setFileName("director.txt");
        attachment1.setFileSize(295);
        AttachmentMeta attachment2 = new AttachmentMeta();
        attachment2.setOid("dd1bf348-48c8-471c-bb64-423fa4753818");
        attachment2.setFileName("teach.txt");
        attachment2.setFileSize(468);
        attachments.add(attachment1);
        attachments.add(attachment2);
        return attachments;
    }
}