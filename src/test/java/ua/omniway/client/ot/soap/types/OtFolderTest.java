package ua.omniway.client.ot.soap.types;

import org.junit.jupiter.api.Test;
import ua.omniway.models.ot.ServiceRequest;

import static org.assertj.core.api.Assertions.assertThat;

class OtFolderTest {

    @Test
    void whenGetAbsolutPath_thenSuccess() {
        assertThat(ServiceRequest.FOLDER.getAbsolutPath()).isEqualTo("ProcessData\\ServiceRequest");
    }
}