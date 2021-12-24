package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.MessagingDirection;
import ua.omniway.models.db.InteractionsDraft;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static ua.omniway.TestUtils.readMessage;

@DataJpaTest
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/interactions_draft/insert.sql"),
        @Sql(scripts = {"classpath:sql/interactions_draft/delete.sql"}, executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED))
})
class InteractionsDraftRepositoryTest {
    @Autowired
    private InteractionsDraftRepository repository;
    @Autowired
    private DbUserRepository userRepository;

    @Test
    void whenSave_thenIdReturns() {
        final InteractionsDraft draft = new InteractionsDraft();
        draft.setUserId(107L);
        draft.setParentUniqueId(100L);
        draft.setDirection(MessagingDirection.FROM_CUSTOMER);
        draft.setText("some text");
        draft.setTextMessageId(1234);
        draft.setSendMessage(new Message());

        final InteractionsDraft actual = repository.saveAndFlush(draft);

        assertThat(actual.getId()).isNotZero();
    }

    @Test
    void whenFindByTextMessageId_thenOk() {
        final InteractionsDraft draft = repository.findByTextMessageId(303);

        assertThat(draft).isNotNull();
    }

    @Test
    void whenDeleteById_thenOk() {
        repository.deleteById(36L);

        assertThat(repository.findById(36L)).isEmpty();
    }

    @Test
    void whenAlreadyExistingTextMessageId_thenException() {
        final InteractionsDraft draft = new InteractionsDraft();
        draft.setUserId(101L)
                .setTextMessageId(300)
                .setParentUniqueId(1L)
                .setDirection(MessagingDirection.FROM_CUSTOMER)
                .setSendMessage(new Message());

        assertThatExceptionOfType(org.springframework.dao.DataIntegrityViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withCauseInstanceOf(org.hibernate.exception.ConstraintViolationException.class)
                .withRootCauseInstanceOf(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class);
    }

    @Test
    void whenNonExistingUserId_thenException() {
        final InteractionsDraft cache = new InteractionsDraft();
        cache.setUserId(1000L)
                .setParentUniqueId(1L)
                .setDirection(MessagingDirection.TO_CUSTOMER)
                .setSendMessage(readMessage("src\\test\\resources\\json\\Update.json"));

        assertThatExceptionOfType(DataAccessException.class)
                .isThrownBy(() -> repository.saveAndFlush(cache))
                .withRootCauseInstanceOf(org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException.class);
    }

    @Test
    void whenNullDirection_thenException() {
        final InteractionsDraft draft = new InteractionsDraft();
        draft.setUserId(1000L)
                .setParentUniqueId(1L)
                .setDirection(null)
                .setSendMessage(readMessage("src\\test\\resources\\json\\Update.json"));

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft));
    }

    @Test
    void whenNullParentUniqueId_thenException() {
        final InteractionsDraft draft = new InteractionsDraft();
        draft.setUserId(100L)
                .setDirection(MessagingDirection.FROM_CUSTOMER)
                .setSendMessage(new Message());

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("InteractionsDraft.parentUniqueId can not be null");
    }

    @Test
    void whenNullUserId_thenException() {
        final InteractionsDraft draft = new InteractionsDraft();
        draft.setParentUniqueId(1L)
                .setDirection(MessagingDirection.FROM_CUSTOMER)
                .setSendMessage(new Message());

        assertThatExceptionOfType(javax.validation.ConstraintViolationException.class)
                .isThrownBy(() -> repository.saveAndFlush(draft))
                .withMessageContaining("InteractionsDraft.userId can not be null");
    }

    @Test
    void whenDeleteUser_draftDeletedAlso() {
        userRepository.deleteById(102L);

        assertThat(repository.findById(33L)).isEmpty();
    }
}