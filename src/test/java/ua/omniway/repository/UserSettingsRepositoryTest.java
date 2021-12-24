package ua.omniway.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import ua.omniway.models.db.UserSetting;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@Transactional
@SqlGroup({
        @Sql("classpath:sql/users/insert.sql"),
        @Sql("classpath:sql/user_settings/insert.sql")
})
class UserSettingsRepositoryTest {
    @Autowired
    private UserSettingsRepository repository;

    @Test
    void whenNonExistingUserId_thenException() {
        final UserSetting setting = new UserSetting(1000L, "en");
        assertThatExceptionOfType(DataAccessException.class).isThrownBy(() -> repository.saveAndFlush(setting));
    }

    @Test
    void whenNullOrEmptyLanguage_thenException() {
        final UserSetting emptyLang = new UserSetting(100L, "");
        final UserSetting nullLang = new UserSetting(101L, null);
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(emptyLang));
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() -> repository.saveAndFlush(nullLang));
    }
}