package ua.omniway.bot.telegram.formatters;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.formatAttachmentSize;

class AttachmentInfoMessageBuilderTest {

    @Test
    void whenFormatSize_thenOk() {
        int bytes = 4;
        int kbytes = 4_098;
        int mbytes = 12_987_001;
        int gbytes = 1_876_498_003;

        assertThat(formatAttachmentSize(bytes)).isEqualTo("4 B");
        assertThat(formatAttachmentSize(kbytes)).isEqualTo(String.format("%.1f KB", kbytes / 1024d));
        assertThat(formatAttachmentSize(mbytes)).isEqualTo(String.format("%.1f MB", mbytes / (1024d * 1024d)));
        assertThat(formatAttachmentSize(gbytes)).isEqualTo(gbytes + " B");
    }
}