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
import ua.omniway.bot.telegram.keyboards.ReplyKeyboards;
import ua.omniway.dao.ot.SubscribersApi;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.UserSetting;
import ua.omniway.services.app.DbUserService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class LocalizationSetter implements CallbackQueryHandler {
    private final DbUserService userService;
    private final SubscribersApi subscribersApiService;

    @Autowired
    public LocalizationSetter(DbUserService userService, SubscribersApi subscribersApiService) {
        this.userService = userService;
        this.subscribersApiService = subscribersApiService;
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public String getCommand() {
        return "/l10n";
    }

    /**
     * args[0] = localization code
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", query.getFrom().getId(), this.getCommand());
        DbUser dbUser = context.getDbUser();
        UserSetting setting = dbUser.getSetting();
        setting.setLanguage(args[0]);
        try {
            subscribersApiService.setLocalization(dbUser.getSubscriberId(), args[0]);
            userService.updateSetting(setting);
            SendMessage answer = new SendMessage();
            answer.setChatId(Long.toString(query.getMessage().getChatId()));
            answer.setText(L10n.getString("commands.settings.language.success", setting.getLanguage()));
            answer.setReplyMarkup(ReplyKeyboards.mainMenu(setting.getLanguage()));
            bot.execute(answer);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            AnswerCallbackQuery.builder().callbackQueryId(query.getId())
                    .showAlert(true)
                    .text(L10n.getString("commands.settings.language.error", setting.getLanguage()));
        } finally {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
        }
    }
}
