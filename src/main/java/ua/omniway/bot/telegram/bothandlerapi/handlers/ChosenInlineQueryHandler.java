package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.ChosenInlineQuery;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;

public interface ChosenInlineQueryHandler extends TelegramHandler {
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
    boolean onChosenInlineQuery(AbstractTelegramBot bot, Update update, ChosenInlineQuery query, DefaultDbContext context) throws Exception;
}
