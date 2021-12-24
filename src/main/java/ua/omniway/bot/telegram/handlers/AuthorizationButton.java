package ua.omniway.bot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.DbUserService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class AuthorizationButton implements CallbackQueryHandler {
    private final DbUserService userService;

    @Autowired
    public AuthorizationButton(DbUserService userService) {
        this.userService = userService;
    }

    @Override
    public String getCommand() {
        return "/authorize";
    }

    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", query.getFrom().getId(), this.getCommand());
        final DbUser dbUser = context.getDbUser();
        if (args.length > 0 && !args[0].isEmpty()) {
            String authType = args[0];
            if ("password".equals(authType)) {
                dbUser.setActiveAction(ActiveAction.LOGIN_PASSWORD);
            } else if ("phone".equals(authType)) {
                dbUser.setActiveAction(ActiveAction.LOGIN_PHONE_NUMBER);
            }
            userService.updateDbUser(dbUser);
            bot.execute(SendMessage.builder()
                    .text(L10n.getString("commands.authorization.message", dbUser.getSetting().getLanguage()))
                    .chatId(Long.toString(query.getMessage().getChatId()))
                    .build());
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
