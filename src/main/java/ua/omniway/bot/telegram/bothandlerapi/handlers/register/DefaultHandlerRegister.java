package ua.omniway.bot.telegram.bothandlerapi.handlers.register;

import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultCommand;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.TelegramHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultHandlerRegister implements HandlerRegister {
    private final List<TelegramHandler> handlers = new ArrayList<>();

    public DefaultHandlerRegister() {
    }

    @Override
    public void addHandler(TelegramHandler handler) {
        handlers.add(handler);
    }

    @Override
    public Collection<TelegramHandler> getHandlers() {
        return Collections.unmodifiableCollection(handlers);
    }

    /**
     * Returns a {@code T} and verifies for access level if any of the handlers implements {@link TelegramHandler}
     *
     * @param command the command name
     * @return {@link CommandHandler} command handler from the collection of handlers, {@code null} if not found
     */
    @Override
    public <T extends DefaultCommand> T findByName(Class<T> clazz, String command, DefaultDbContext context) {
        return handlers.stream()
                .filter(clazz::isInstance)
                .filter(handler -> context.getDbUser().getActiveAction().getId() >= handler.getRequiredAccessLevel())
                .map(clazz::cast)
                .filter(handler -> handler.getCommand().equalsIgnoreCase(command))
                .findFirst().orElse(null);
    }

    /**
     * Returns a {@code List<T>} and verifies for access level if any of the handlers implements {@link TelegramHandler}
     *
     * @param clazz the class of the handler
     * @return {@code List<T>} with all handlers implementing the generic type provided
     */
    @Override
    public <T extends TelegramHandler> List<T> findByClass(Class<T> clazz, DefaultDbContext context) {
        return handlers.stream()
                .filter(clazz::isInstance)
                .filter(handler -> context.getDbUser().getActiveAction().getId() >= handler.getRequiredAccessLevel())
                .map(clazz::cast)
                .collect(Collectors.toList());
    }
}
