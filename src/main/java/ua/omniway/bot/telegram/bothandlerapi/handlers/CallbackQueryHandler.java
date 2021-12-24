package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;

public interface CallbackQueryHandler extends DefaultCommand {

    /**
     * Executes event handler method. Prepares command args
     *
     * @param bot     bot
     * @param update  update
     * @param message message string
     * @param context update handler context
     * @throws Exception any exception inside
     */
    default void executeCallbackEvent(AbstractTelegramBot bot, Update update, String message, DefaultDbContext context) throws Exception {
        this.onCallbackQuery(bot, update, update.getCallbackQuery(), this.getParseArgs(message), context);
    }

    /**
     * Fired whenever bot receives a callback query
     * /command_arg0_arg1_arg2
     *
     * @param bot     bot
     * @param update  update
     * @param query   query
     * @param args    arguments after command
     * @param context update handler context
     * @throws Exception any exception inside
     */
    void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception;
}
