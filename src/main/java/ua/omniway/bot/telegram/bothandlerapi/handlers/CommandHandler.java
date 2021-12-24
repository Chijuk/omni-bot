package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;

public interface CommandHandler extends DefaultCommand {

    /**
     * Executes event handler method. Prepares command args
     *
     * @param bot     bot
     * @param update  update
     * @param message message string
     * @param context update handler context
     * @throws Exception any exception inside
     */
    default void executeCommandEvent(AbstractTelegramBot bot, Update update, String message, DefaultDbContext context) throws Exception {
        this.onCommandMessage(bot, update, update.getMessage(), this.getParseArgs(message), context);
    }

    /**
     * Fired when user types in /command_arg0_arg1_arg2..
     *
     * @param bot     bot
     * @param update  update
     * @param message message
     * @param args    arguments after command
     * @param context update handler context
     * @throws Exception any exception inside
     */
    void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception;
}
