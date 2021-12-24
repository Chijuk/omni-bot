package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.db.ServiceCallFeedbackDraft;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@DataJpaTest
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/service_call_feedback_drafts/insert.sql"),
        @Sql(scripts = {"classpath:sql/service_call_feedback_drafts/delete.sql"}, executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED))
})
class ServiceCallFeedbackDraftRepositoryTest {
    @Autowired
    private ServiceCallFeedbackDraftRepository repository;
    @Autowired
    private DbUserRepository userRepository;

    @Test
    void whenSave_thenIdReturns() {
        final ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
        draft.setUserId(100L);
        draft.setParentUniqueId(100L);
        draft.setWizardMessage(new Message());

        final ServiceCallFeedbackDraft actual = repository.saveAndFlush(draft);

        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenFindByTextMessageId_thenOk() {
        final ServiceCallFeedbackDraft draft = repository.findByTextMessageId(3003);

        assertThat(draft).isNotNull();
    }

    @Test
    void whenDeleteById_thenOk() {
        repository.deleteById(56L);

        assertThat(repository.findById(56L)).isEmpty();
    }

    @Test
    void whenAlreadyExistingTextMessageId_thenException() {
        final ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
        draft.setUserId(101L)
                .setTextMessageId(3000)
                .setParentUniqueId(1L)
                .setWizardMessage(new Message());


        assertThatExceptionOfType(org.springframework.dao.DataIntegrityViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withCauseInstanceOf(org.hibernate.exception.ConstraintViolationException.class)
                .withRootCauseInstanceOf(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class);
    }

    @Test
    void whenNonExistingUserId_thenException() {
        final ServiceCallFeedbackDraft cache = new ServiceCallFeedbackDraft();
        cache.setUserId(1000L)
                .setParentUniqueId(1L)
                .setWizardMessage(new Message());

        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> repository.saveAndFlush(cache))
                .withRootCauseInstanceOf(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class);

    }

    @Test
    void whenNullParentUniqueId_thenException() {
        final ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
        draft.setUserId(100L);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("ServiceCallFeedbackDraft.parentUniqueId can not be null");
    }

    @Test
    void whenNullWizardMessage_thenException() {
        final ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
        draft.setUserId(100L);
        draft.setParentUniqueId(1234L);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("ServiceCallFeedbackDraft.wizardMessage can not be null");
    }

    @Test
    void whenNullUserId_thenException() {
        final ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
        draft.setParentUniqueId(1L);
        draft.setWizardMessage(new Message());

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("ServiceCallFeedbackDraft.userId can not be null");
    }

    @Test
    void whenDeleteUser_draftDeletedAlso() {
        userRepository.deleteById(102L);

        assertThat(repository.findById(52L)).isEmpty();
    }
}