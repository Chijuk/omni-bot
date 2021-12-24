package ua.omniway.rest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.omniway.bot.telegram.NotificationUtils;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.UserSetting;
import ua.omniway.services.app.DbUserService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.omniway.TestUtils.readResource;

@WebMvcTest(NotifyService.class)
class NotifyApprovalVoteTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbUserService userService;

    @MockBean
    private NotificationUtils notificationUtils;

    @Test
    void whenApprovalVoteNotify_thenOk() throws Exception {
        DbUser dbUser = new DbUser();
        dbUser.setChatId(50).setSetting(new UserSetting("uk"));

        when(userService.getDbUserByPersonId(300L)).thenReturn(dbUser);

        doNothing().when(notificationUtils).notify(any(SendMessage.class), any(DbUser.class));

        mockMvc.perform(post("/notify/approval-votes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResource("json/ApprovalVoteNotification/ok.json"))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isOk());

        verify(userService, times(1)).getDbUserByPersonId(300L);
    }

    @Test
    void whenEventNull_thenException() throws Exception {
        mockMvc.perform(post("/notify/approval-votes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResource("json/ApprovalVoteNotification/nullEvent.json"))
                        .characterEncoding("utf-8")
                )
                .andExpect(status().isPreconditionFailed())
                .andExpect(content().string(containsString(
                        "Field error in object 'approvalVoteNotification' on field 'event': rejected value [null]")));
    }
}
