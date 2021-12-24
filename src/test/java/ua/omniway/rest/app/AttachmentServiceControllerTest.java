package ua.omniway.rest.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.omniway.services.app.FileDownloadService;
import ua.omniway.services.exceptions.ServiceException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttachmentService.class)
class AttachmentServiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FileDownloadService downloadService;

    @Test
    void whenNullOrEmptyOid_thenResponse400() throws Exception {
        doThrow(ServiceException.class).when(downloadService).getAttachment("error");

        mockMvc.perform(get("/attachments/{oid}", "error"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("ua.omniway.services.exceptions.ServiceException")));

        verify(downloadService, times(1)).getAttachment("error");
    }

    @Test
    void whenGetMethod_405() throws Exception {
        mockMvc.perform(get("/attachments"))
                .andExpect(status().isMethodNotAllowed());
    }
}