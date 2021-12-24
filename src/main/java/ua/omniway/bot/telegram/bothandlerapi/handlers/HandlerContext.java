package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public interface HandlerContext {
    /**
     * returns context object from persistence storage
     */
    DefaultDbContext init(Update update, User user) throws ContextInitializationException;

    /**
     * returns updated object from persistence storage
     */
    DefaultDbContext refresh(Update update, User user) throws ContextInitializationException;
}
