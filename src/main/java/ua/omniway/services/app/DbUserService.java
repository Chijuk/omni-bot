package ua.omniway.services.app;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.UserSetting;
import ua.omniway.models.db.UserStatus;
import ua.omniway.repository.DbUserRepository;
import ua.omniway.repository.UserSettingsRepository;

@Service
public class DbUserService {

    private final DbUserRepository dbUserDao;
    private final UserSettingsRepository settingsDao;
    private final CommonDraftsService cacheService;

    @Autowired
    public DbUserService(DbUserRepository dbUserDao, UserSettingsRepository settingsDao, CommonDraftsService cacheService) {
        this.dbUserDao = dbUserDao;
        this.settingsDao = settingsDao;
        this.cacheService = cacheService;
    }

    /**
     * @param user user from Telegram API
     * @return db user saved in db with defaults
     */
    public DbUser createDefaultDbUser(@NonNull User user) {
        DbUser dbUser = new DbUser(user.getId(), user.getId(), ActiveAction.START, UserStatus.ENABLED,
                new UserSetting(user.getId(), L10n.DEFAULT_LANGUAGE_CODE)
        );
        dbUserDao.saveAndFlush(dbUser);
        return dbUser;
    }

    /**
     * @param userId value from Telegram API
     * @return {@link DbUser} object from DB.
     * May be null if User not found or joined UserSettings object not found
     */
    public DbUser getDbUser(Long userId) {
        return dbUserDao.findById(userId).orElse(null);
    }

    /**
     * @param otUniqueId identifier from OMNITRACKER
     * @return {@link DbUser} from DB if its exists. {@code null} if not found
     */
    public DbUser getDbUserByPersonId(long otUniqueId) {
        return dbUserDao.findByPersonId(otUniqueId);
    }

    /**
     * Update user inDB
     *
     * @param dbUser object to update
     */
    public void updateDbUser(@NonNull DbUser dbUser) {
        dbUserDao.saveAndFlush(dbUser);
    }

    /**
     * Clean cache and reset dbUser active action
     *
     * @param dbUser dbUser to reset
     */
    public void resetDbUser(@NonNull DbUser dbUser) {
        DbUser cachedDbUser = getDbUser(dbUser.getUserId());
        cachedDbUser.setActiveAction(ActiveAction.MAIN_MENU);   // reset active state
        this.updateDbUser(cachedDbUser);
        cacheService.cleanAllDraftObjects(dbUser);
    }

    /**
     * Update user settings in DB
     *
     * @param userSetting object to update
     */
    public void updateSetting(@NonNull UserSetting userSetting) {
        settingsDao.saveAndFlush(userSetting);
    }
}
