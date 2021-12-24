package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;

public interface InlineQueryHandler extends TelegramHandler {
    /**
     * Fired whenever bot receives a callback query
     *
     * @param bot     bot
     * @param update  update
     * @param query   query
     * @param context update handler context
     * @return {@code true} whenever this even has to be consumed, {@code false} to continue notified other handlers
     * @throws Exception any exception inside
     */
    boolean onInlineQuery(AbstractTelegramBot bot, Update update, InlineQuery query, DefaultDbContext context) throws Exception;
}
