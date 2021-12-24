package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.omniway.models.db.ApprovalVoteTemp;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/approval_vote_temp/insert.sql")
})
class ApprovalVoteTempRepositoryTest {
    @Autowired
    private ApprovalVoteTempRepository repository;

    @Test
    void whenNonExistingUserId_thenException() {
        final ApprovalVoteTemp vote = new ApprovalVoteTemp();
        vote.setUserId(1000L)
                .setOtUniqueId(1L)
                .setMessage(new Message());
        assertThatExceptionOfType(DataAccessException.class).isThrownBy(() -> repository.saveAndFlush(vote));
    }

    @Test
    void whenNullMessage_thenException() {
        final ApprovalVoteTemp vote = new ApprovalVoteTemp();
        vote.setUserId(100L)
                .setOtUniqueId(1L)
                .setMessage(null);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(vote));
    }
}