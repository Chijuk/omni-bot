package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.ServiceCallDraft;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql")
})
class GeneralTests {
    @Autowired
    private ServiceCallDraftRepository draftRepository;
    @Autowired
    private AttachmentInfoRepository attachmentInfoRepository;

    @Test
    void whenSaveObjectWithId_thenSequenceAllocated() {
        ServiceCallDraft draft1 = new ServiceCallDraft();
        draft1.setWizardMessage(new Message());
        draft1.setUserId(104L);
        ServiceCallDraft draft2 = new ServiceCallDraft();
        draft2.setWizardMessage(new Message());
        draft2.setUserId(103L);

        draft1 = draftRepository.saveAndFlush(draft1);

        AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(draft1.getId());
        attachment.setUserId(105L);
        attachment.setReplyMessageId(900);

        attachment = attachmentInfoRepository.saveAndFlush(attachment);
        draft2 = draftRepository.saveAndFlush(draft2);

        assertThat(draft1.getId()).isEqualTo(1);
        assertThat(attachment.getId()).isEqualTo(2);
        assertThat(draft2.getId()).isEqualTo(3);
    }
}
