package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;

public interface EditedMessageHandler extends TelegramHandler {
    /**
     * Fired whenever user types anything but a command
     *
     * @param bot     bot
     * @param update  update
     * @param message message
     * @param context update handler context
     * @return {@code true} if edit was successful, aborting notification to other handlers,
     * {@code false} otherwise, continuing to look for handler that would return {@code true}
     * @throws Exception any exception inside
     */
    boolean onEditMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception;
}
