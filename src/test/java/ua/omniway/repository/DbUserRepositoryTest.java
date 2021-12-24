package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.UserStatus;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@Transactional
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/user_settings/insert.sql")
})
class DbUserRepositoryTest {
    @Autowired
    private DbUserRepository userRepository;

    @Test
    void whenFindByPersonOtUniqueId_thenOk() {
        final DbUser dbUser = userRepository.findByPersonId(307L);
        assertThat(dbUser).isNotNull();
        assertThat(dbUser.getSetting().getLanguage()).isEqualTo("uk");
    }

    @Test
    void whenNullAction_thenException() {
        final DbUser dbUser = new DbUser();
        dbUser.setUserId(110L)
                .setChatId(110L)
                .setSubscriberId(110L)
                .setPersonId(110L)
                .setActiveAction(null)
                .setIsActive(UserStatus.ENABLED);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> userRepository.saveAndFlush(dbUser))
                .withMessageContaining("DbUser.ActiveAction can not be null");
    }

    @Test
    void whenNullActiveState_thenException() {
        final DbUser dbUser = new DbUser();
        dbUser.setUserId(110L)
                .setChatId(110L)
                .setSubscriberId(110L)
                .setPersonId(110L)
                .setActiveAction(ActiveAction.START)
                .setIsActive(null);

        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> userRepository.saveAndFlush(dbUser))
                .withMessageContaining("DbUser.UserStatus can not be null");
    }
}