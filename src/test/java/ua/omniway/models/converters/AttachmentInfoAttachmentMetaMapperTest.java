package ua.omniway.models.converters;

import org.junit.jupiter.api.Test;
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.models.db.AttachmentInfo;

import static org.assertj.core.api.Assertions.assertThat;
import static ua.omniway.models.converters.AttachmentInfoAttachmentMetaMapper.INSTANCE;

class AttachmentInfoAttachmentMetaMapperTest {

    @Test
    void whenConvertToAttachmentMeta_thenSuccess() {
        final AttachmentInfo attachmentInfo = new AttachmentInfo();
        attachmentInfo.setId(1L);
        attachmentInfo.setParentId(5L);
        attachmentInfo.setUserId(105L);
        attachmentInfo.setReplyMessageId(900);
        attachmentInfo.setOid("123");
        attachmentInfo.setFileName("attachment_name");
        attachmentInfo.setFileSize(10987);
        final AttachmentMeta attachmentMeta = INSTANCE.toAttachmentMeta(attachmentInfo);
        assertThat(attachmentMeta).isEqualToComparingFieldByField(attachmentInfo);
    }

}