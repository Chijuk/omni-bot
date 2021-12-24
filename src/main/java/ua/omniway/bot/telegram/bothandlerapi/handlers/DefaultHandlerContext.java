package ua.omniway.bot.telegram.bothandlerapi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.DbUserService;

@Slf4j
@Component
public class DefaultHandlerContext implements HandlerContext {
    private final DbUserService userService;

    @Autowired
    public DefaultHandlerContext(DbUserService userService) {
        this.userService = userService;
    }

    @Override
    public DefaultDbContext init(Update update, User user) {
        log.debug("Initializing handle context for: {}", user.getId());
        DbUser dbUser = userService.getDbUser(user.getId());
        if (dbUser == null) {
            dbUser = userService.createDefaultDbUser(user); // if not exists in db - crate default
        }
        return DefaultDbContext.builder().user(user).dbUser(dbUser).build();
    }

    @Override
    public DefaultDbContext refresh(Update update, User user) throws ContextInitializationException {
        log.debug("Refreshing handle context for: {}", user.getId());
        DbUser dbUser = userService.getDbUser(user.getId());
        if (dbUser == null) {
            throw new ContextInitializationException("User not found in DB: " + user);
        }
        return DefaultDbContext.builder().user(user).dbUser(dbUser).build();
    }
}
