package ua.omniway.bot.telegram.bothandlerapi.handlers;

import lombok.Builder;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.omniway.models.db.DbUser;

@Data
@Builder
public class DefaultDbContext {
    private User user;
    private DbUser dbUser;

    public DefaultDbContext(User user, DbUser dbUser) {
        this.user = user;
        this.dbUser = dbUser;
    }
}
