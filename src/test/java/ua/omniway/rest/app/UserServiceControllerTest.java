package ua.omniway.rest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.omniway.bot.telegram.NotificationUtils;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.UserSetting;
import ua.omniway.services.app.DbUserService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserService.class)
class UserServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbUserService userService;

    @MockBean
    private NotificationUtils notificationUtils;

    @Test
    void whenDeactivateUser_thenOk() throws Exception {
        DbUser dbUser = new DbUser();
        dbUser.setChatId(50).setSetting(new UserSetting("uk"));

        when(userService.getDbUserByPersonId(300L)).thenReturn(dbUser);

        doNothing().when(notificationUtils).notify(any(SendMessage.class), any(DbUser.class));

        mockMvc.perform(post("/users/deactivate/{otUniqueId}", 300L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Done!")));

        verify(userService, times(1)).getDbUserByPersonId(300L);
    }

    @Test
    void whenDeactivateUnknownUser_thenException() throws Exception {
        doReturn(null).when(userService).getDbUserByPersonId(100L);
        mockMvc.perform(post("/users/deactivate/{otUniqueId}", 100L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User not found in DB")));

        verify(userService, times(1)).getDbUserByPersonId(100L);
        verifyNoInteractions(notificationUtils);
    }

    @Test
    void whenReactivateUser_thenOk() throws Exception {
        DbUser dbUser = new DbUser();
        dbUser.setChatId(50).setSetting(new UserSetting("uk"));

        when(userService.getDbUserByPersonId(300L)).thenReturn(dbUser);

        doNothing().when(notificationUtils).notify(any(SendMessage.class), any(DbUser.class));

        mockMvc.perform(post("/users/reactivate/{otUniqueId}", 300L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Done!")));

        verify(userService, times(1)).getDbUserByPersonId(300L);
    }

    @Test
    void whenReactivateUnknownUser_thenException() throws Exception {
        doReturn(null).when(userService).getDbUserByPersonId(100L);
        mockMvc.perform(post("/users/reactivate/{otUniqueId}", 100L))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User not found in DB")));

        verify(userService, times(1)).getDbUserByPersonId(100L);
        verifyNoInteractions(notificationUtils);
    }

    @Test
    void whenGetMethod_405() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void whenDeleteMethod_405() throws Exception {
        mockMvc.perform(delete("/users"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void whenPutMethod_405() throws Exception {
        mockMvc.perform(put("/users"))
                .andExpect(status().isMethodNotAllowed());
    }
}