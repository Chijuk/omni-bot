package ua.omniway.bot.telegram.bothandlerapi.handlers.register;

import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultCommand;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.TelegramHandler;

import java.util.Collection;
import java.util.List;

public interface HandlerRegister {
    void addHandler(TelegramHandler handler);

    Collection<TelegramHandler> getHandlers();

    <T extends DefaultCommand> T findByName(Class<T> clazz, String command, DefaultDbContext context);

    <T extends TelegramHandler> List<T> findByClass(Class<T> clazz, DefaultDbContext context);
}
