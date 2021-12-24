package ua.omniway.rest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.omniway.bot.telegram.NotificationUtils;
import ua.omniway.services.app.DbUserService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.omniway.TestUtils.readResource;

@WebMvcTest(NotifyService.class)
class NotificationRequestTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbUserService userService;

    @MockBean
    private NotificationUtils notificationUtils;

    @Test
    void whenMessageBodyEmpty_thenException() throws Exception {
        mockMvc.perform(post("/notify/approval-votes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResource("json/NotificationRequests/emptyMessage.json"))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().string(containsString(
                        "Field error in object 'approvalVoteNotification' on field 'messageBody': rejected value []")));
    }

    @Test
    void whenMessageBodyNull_thenException() throws Exception {
        mockMvc.perform(post("/notify/approval-votes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResource("json/NotificationRequests/nullMessage.json"))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().string(containsString(
                        "Field error in object 'approvalVoteNotification' on field 'messageBody': rejected value [null]")));
    }

    @Test
    void whenPersonIdIsUnknown_thenException() throws Exception {
        doReturn(null).when(userService).getDbUserByPersonId(100L);

        mockMvc.perform(post("/notify/approval-votes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResource("json/NotificationRequests/unknownUserUniqueId.json"))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("is not DB user")));

        verify(userService, times(1)).getDbUserByPersonId(100L);
        verifyNoInteractions(notificationUtils);
    }
}
