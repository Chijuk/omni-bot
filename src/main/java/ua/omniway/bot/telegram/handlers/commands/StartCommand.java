package ua.omniway.bot.telegram.handlers.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.keyboards.InlineKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.CommonDraftsService;
import ua.omniway.services.app.DbUserService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class StartCommand implements CommandHandler {
    private final DbUserService userService;
    private final CommonDraftsService cacheService;

    @Autowired
    public StartCommand(DbUserService userService, CommonDraftsService cacheService) {
        this.userService = userService;
        this.cacheService = cacheService;
    }

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", context.getUser().getId(), this.getCommand());
        final DbUser dbUser = context.getDbUser();
        bot.execute(SendMessage.builder()
                .chatId(Long.toString(message.getChat().getId()))
                .text(L10n.getString("bot.welcome.message", dbUser.getSetting().getLanguage()))
                .replyMarkup(InlineKeyboards.welcomeOptionsList(dbUser.getSetting().getLanguage()))
                .build());
        cacheService.cleanAllDraftObjects(dbUser);                 // clean cache if it exists
        dbUser.setActiveAction(ActiveAction.START);
        userService.updateDbUser(dbUser);
    }
}
