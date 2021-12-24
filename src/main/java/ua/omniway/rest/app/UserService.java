package ua.omniway.rest.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.NotificationUtils;
import ua.omniway.bot.telegram.keyboards.InlineKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.UserStatus;
import ua.omniway.services.app.DbUserService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserService {
    private final DbUserService dbUserService;
    private final NotificationUtils notificationUtils;

    @Autowired
    public UserService(DbUserService dbUserService, NotificationUtils notificationUtils) {
        this.dbUserService = dbUserService;
        this.notificationUtils = notificationUtils;
    }

    @PostMapping(path = "/deactivate/{otUniqueId}")
    public ResponseEntity<String> deactivateUser(@PathVariable("otUniqueId") long otUniqueId) throws TelegramApiException {
        log.info(LogMarkers.OT_ACTIVITY, "[{}] deactivating otUniqueId", otUniqueId);
        DbUser dbUser = dbUserService.getDbUserByPersonId(otUniqueId);
        if (dbUser != null) {
            dbUser.setIsActive(UserStatus.DISABLED);
            dbUser.setActiveAction(ActiveAction.START);
            dbUserService.updateDbUser(dbUser);
            notificationUtils.notify(SendMessage.builder()
                    .chatId(String.valueOf(dbUser.getChatId()))
                    .text(L10n.getString("bot.user.deactivation.message", dbUser.getSetting().getLanguage()))
                    .replyMarkup(null)
                    .build(), dbUser);
            return ResponseEntity.ok().body("Done!");
        } else {
            return ResponseEntity.ok().body("User not found in DB");
        }
    }

    @PostMapping(path = "/reactivate/{otUniqueId}")
    public ResponseEntity<String> activateUser(@PathVariable("otUniqueId") long otUniqueId) throws TelegramApiException {
        log.info(LogMarkers.OT_ACTIVITY, "[{}] activating otUniqueId", otUniqueId);
        DbUser dbUser = dbUserService.getDbUserByPersonId(otUniqueId);
        if (dbUser != null) {
            String lang = dbUser.getSetting().getLanguage();
            dbUser.setIsActive(UserStatus.ENABLED);
            dbUser.setActiveAction(ActiveAction.START);
            dbUserService.updateDbUser(dbUser);
            notificationUtils.notify(SendMessage.builder()
                    .chatId(String.valueOf(dbUser.getChatId()))
                    .text(L10n.getString("bot.user.reset.message", lang) + "\n\n" +
                            L10n.getString("bot.welcome.message", lang))
                    .replyMarkup(InlineKeyboards.welcomeOptionsList(lang))
                    .build(), dbUser);
            return ResponseEntity.ok().body("Done!");
        } else {
            return ResponseEntity.ok().body("User not found in DB");
        }
    }

    @GetMapping
    public ResponseEntity<String> getUser() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @PutMapping
    public ResponseEntity<String> putUser() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}