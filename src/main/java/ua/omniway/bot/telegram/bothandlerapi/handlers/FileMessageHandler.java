package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;

public interface FileMessageHandler extends TelegramHandler {
    Integer TELEGRAM_FILE_SIZE_LIMIT = 15_728_640;

    boolean onFileSent(AbstractTelegramBot bot, Update update, DefaultDbContext context) throws Exception;

    default boolean isSizeAccepted(Integer fileSize) {
        return TELEGRAM_FILE_SIZE_LIMIT > fileSize;
    }
}
