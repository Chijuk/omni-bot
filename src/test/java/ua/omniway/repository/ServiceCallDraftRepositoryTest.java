package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.db.ServiceCallDraft;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@DataJpaTest
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/service_calls_draft/insert.sql"),
        @Sql(scripts = {"classpath:sql/service_calls_draft/delete.sql"}, executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED))
})
class ServiceCallDraftRepositoryTest {
    @Autowired
    private ServiceCallDraftRepository repository;
    @Autowired
    private DbUserRepository userRepository;

    @Test
    void whenSave_thenIdReturns() {
        final ServiceCallDraft draft = new ServiceCallDraft();
        draft.setUserId(107L);
        draft.setSummary("some text");
        draft.setSummaryMessageId(1234);
        draft.setWizardMessage(new Message());

        final ServiceCallDraft actual = repository.saveAndFlush(draft);

        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenFindById_thenOk() {
        final ServiceCallDraft draft = repository.findById(75L).orElse(null);

        assertThat(draft).isNotNull();
    }

    @Test
    void whenFindBySummaryMessageId_thenOk() {
        final ServiceCallDraft draft = repository.findBySummaryMessageId(203);

        assertThat(draft.getSummary()).isEqualTo("summary text 4");
    }

    @Test
    void whenExistingUserDraft_thenOk() {
        final ServiceCallDraft draft = new ServiceCallDraft();
        draft.setUserId(101L);
        draft.setSummary("some text");
        draft.setSummaryMessageId(1234);
        draft.setWizardMessage(new Message());

        final ServiceCallDraft actual = repository.saveAndFlush(draft);

        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenNonExistingUserId_thenException() {
        final ServiceCallDraft draft = new ServiceCallDraft();
        draft.setUserId(10005L);
        draft.setSummary("some text");
        draft.setSummaryMessageId(1234);
        draft.setWizardMessage(new Message());

        assertThatExceptionOfType(org.springframework.dao.DataIntegrityViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withCauseInstanceOf(org.hibernate.exception.ConstraintViolationException.class)
                .withRootCauseInstanceOf(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class);
    }

    @Test
    void whenNullMessage_thenException() {
        final ServiceCallDraft draft = new ServiceCallDraft();
        draft.setUserId(105L);
        draft.setSummary("some text");
        draft.setSummaryMessageId(1234);
        draft.setWizardMessage(null);

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("ServiceCallDraft.wizardMessage can not be null");
    }

    @Test
    void whenNullUserId_thenException() {
        final ServiceCallDraft draft = new ServiceCallDraft();
        draft.setSummary("some text");
        draft.setSummaryMessageId(1234);
        draft.setWizardMessage(new Message());

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("ServiceCallDraft.userId can not be null");
    }

    @Test
    void whenSummaryNull_thenOk() {
        final ServiceCallDraft draft = new ServiceCallDraft();
        draft.setUserId(108L);
        draft.setWizardMessage(new Message());

        assertThat(repository.saveAndFlush(draft).getId()).isNotZero();
    }

    @Test
    void whenDeleteUser_draftDeletedAlso() {
        userRepository.deleteById(102L);

        assertThat(repository.findById(73L).orElse(null)).isNull();
    }
}