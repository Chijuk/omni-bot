package ua.omniway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.omniway.models.db.UserSetting;

public interface UserSettingsRepository extends JpaRepository<UserSetting, Long> {
}
