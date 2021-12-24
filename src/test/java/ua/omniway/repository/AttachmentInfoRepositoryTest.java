package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.MessagingDirection;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.InteractionsDraft;
import ua.omniway.models.db.ServiceCallDraft;
import ua.omniway.models.db.ServiceCallFeedbackDraft;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@DataJpaTest
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/service_calls_draft/insert.sql"),
        @Sql("classpath:sql/interactions_draft/insert.sql"),
        @Sql("classpath:sql/service_call_feedback_drafts/insert.sql"),
        @Sql("classpath:sql/attachments_info/insert.sql"),
        @Sql(scripts = {
                "classpath:sql/service_calls_draft/delete.sql",
                "classpath:sql/interactions_draft/delete.sql",
                "classpath:sql/service_call_feedback_drafts/delete.sql",
                "classpath:sql/attachments_info/delete.sql"
        },
                executionPhase = AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = ISOLATED))
})
class AttachmentInfoRepositoryTest {
    @Autowired
    private AttachmentInfoRepository repository;
    @Autowired
    private DbUserRepository userRepository;
    @Autowired
    private ServiceCallDraftRepository draftRepository;
    @Autowired
    private InteractionsDraftRepository interactionRepository;
    @Autowired
    private ServiceCallFeedbackDraftRepository feedbackDraftRepository;

    @Test
    void whenSaveWithDraft_thenIdReturns() {
        ServiceCallDraft draft = new ServiceCallDraft();
        draft.setWizardMessage(new Message());
        draft.setUserId(107L);
        draft = draftRepository.saveAndFlush(draft);

        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(draft.getId());
        attachment.setUserId(105L);
        attachment.setReplyMessageId(900);

        final AttachmentInfo actual = repository.saveAndFlush(attachment);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenSaveWithInteraction_thenIdReturns() {
        InteractionsDraft draft = new InteractionsDraft();
        draft.setParentUniqueId(15L);
        draft.setUserId(107L);
        draft.setDirection(MessagingDirection.FROM_CUSTOMER);
        draft = interactionRepository.saveAndFlush(draft);

        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(draft.getId());
        attachment.setUserId(105L);
        attachment.setReplyMessageId(900);

        final AttachmentInfo actual = repository.saveAndFlush(attachment);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenSaveWithFeedback_thenIdReturns() {
        ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
        draft.setParentUniqueId(15L);
        draft.setUserId(107L);
        draft.setWizardMessage(new Message());
        draft = feedbackDraftRepository.saveAndFlush(draft);

        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(draft.getId());
        attachment.setUserId(105L);
        attachment.setReplyMessageId(900);

        final AttachmentInfo actual = repository.saveAndFlush(attachment);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenDelete_thenOk() {
        repository.deleteById(1L);

        assertThat(repository.findById(1L)).isNotPresent();
    }

    @Test
    void whenFindByReplyMessageId_thenOk() {
        final AttachmentInfo actual = repository.findByReplyMessageId(204);
        assertThat(actual).isNotNull();
    }

    @Test
    void whenFindByParentId_thenOk() {
        assertThat(repository.findByParentId(76L).size()).isEqualTo(2);
    }

    @Test
    void whenExistingReplyMessageId_thenException() {
        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(15L);
        attachment.setUserId(105L);
        attachment.setReplyMessageId(202);

        assertThatExceptionOfType(org.springframework.dao.DataIntegrityViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(attachment))
                .withCauseInstanceOf(org.hibernate.exception.ConstraintViolationException.class)
                .withRootCauseInstanceOf(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class);
    }

    @Test
    void whenNullParentId_thenException() {
        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setUserId(105L);
        attachment.setReplyMessageId(900);

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(attachment))
                .withMessageContaining("AttachmentInfo.parentId can not be null");
    }

    @Test
    void whenNullUserId_thenException() {
        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(15L);
        attachment.setUserId(null);
        attachment.setReplyMessageId(900);

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(attachment))
                .withMessageContaining("AttachmentInfo.userId can not be null");
    }

    @Test
    void whenNullReplyMessageId_thenException() {
        final AttachmentInfo attachment = new AttachmentInfo();
        attachment.setParentId(17L);
        attachment.setUserId(105L);
        attachment.setReplyMessageId(null);

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(attachment))
                .withMessageContaining("AttachmentInfo.replyMessageId can not be null");
    }

    @Test
    void whenDeleteDraft_deleteAttachmentAlso() {
        draftRepository.deleteById(74L);

        assertThat(repository.findByParentId(74L)).isEmpty();
    }

    @Test
    void whenDeleteInteraction_deleteAttachmentAlso() {
        interactionRepository.deleteById(31L);

        assertThat(repository.findByParentId(31L)).isEmpty();
    }

    @Test
    void whenDeleteFeedback_deleteAttachmentAlso() {
        feedbackDraftRepository.deleteById(53L);

        assertThat(repository.findByParentId(53L)).isEmpty();
    }

    @Test
    void whenDeleteUser_deleteAttachmentAlso() {
        userRepository.deleteById(103L);

        assertThat(repository.findByParentId(14L)).isEmpty();
        assertThat(repository.findByParentId(34L)).isEmpty();
        assertThat(repository.findByParentId(53L)).isEmpty();
    }

    @Test
    void whenFindDraft_AttachmentsFoundAlso() {
        final ServiceCallDraft draft = draftRepository.findById(74L).orElse(null);

        assertThat(draft.getAttachments().size()).isEqualTo(2);
    }

    @Test
    void whenFindInteraction_AttachmentsFoundAlso() {
        final InteractionsDraft draft = interactionRepository.findById(31L).orElse(null);

        assertThat(draft.getAttachments().size()).isEqualTo(2);
    }

    @Test
    void whenUpdateFileFields_thenOk() {
        final List<AttachmentInfo> attachments = repository.findByParentId(76L);
        for (AttachmentInfo attach : attachments) {
            attach.setOid("123");
            attach.setFileName("name");
            attach.setFileSize(10987);
        }
        repository.saveAll(attachments);

        assertThat(repository.findByReplyMessageId(205).getOid()).isEqualTo("123");
    }
}