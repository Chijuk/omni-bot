package ua.omniway.bot.telegram.handlers.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.keyboards.InlineKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class SettingsCommand implements CommandHandler, CallbackQueryHandler {

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public String getCommand() {
        return "/settings";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", context.getUser().getId(), this.getCommand());
        bot.execute(SendMessage.builder()
                .chatId(Long.toString(message.getChat().getId()))
                .text(L10n.getString("commands.settings.defaultText", context.getDbUser().getSetting().getLanguage()))
                .replyMarkup(InlineKeyboards.languageList())
                .build());
    }

    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", context.getUser().getId(), this.getCommand());
        bot.execute(SendMessage.builder()
                .chatId(Long.toString(query.getMessage().getChat().getId()))
                .text(L10n.getString("commands.settings.defaultText", context.getDbUser().getSetting().getLanguage()))
                .replyMarkup(InlineKeyboards.languageList())
                .build());
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
