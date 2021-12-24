package ua.omniway.bot.telegram.handlers.menu.servicecalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import static ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards.serviceCallsMenu;

@Slf4j
@Component
public class ShowServiceCallsMenu implements CallbackQueryHandler {
    @Override
    public String getCommand() {
        return "/serviceCallsMenu";
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", query.getFrom().getId(), this.getCommand());
        final DbUser dbUser = context.getDbUser();
        bot.execute(EditMessageText.builder()
                .chatId(Long.toString(query.getMessage().getChat().getId()))
                .messageId(query.getMessage().getMessageId())
                .text(L10n.getString("menu.main.serviceCalls.text", dbUser.getSetting().getLanguage()))
                .replyMarkup(serviceCallsMenu(dbUser.getSetting().getLanguage()))
                .build());
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}