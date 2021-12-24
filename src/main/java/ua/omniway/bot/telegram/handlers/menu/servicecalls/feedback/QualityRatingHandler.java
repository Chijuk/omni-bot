package ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;
import java.util.List;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback.FeedbackCallbackEnums.QUALITY_FEEDBACK_OPTIONS;

@Slf4j
@Component
public class QualityRatingHandler implements CallbackQueryHandler {
    private final ServiceCallsApiService apiService;

    public QualityRatingHandler(ServiceCallsApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public String getCommand() {
        return QUALITY_FEEDBACK_OPTIONS.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = service call uniqueId
     * <br>
     * args[1] = callback data with rating
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        DbUser dbUser = context.getDbUser();
        String lang = dbUser.getSetting().getLanguage();
        try {
            EditMessageText edit = new EditMessageText();
            edit.setChatId(Long.toString(query.getMessage().getChat().getId()));
            edit.setMessageId(query.getMessage().getMessageId());
            String footerText;
            if (apiService.canSetQualityRating(Long.parseLong(args[0]), dbUser.getPersonId())) {
                apiService.setQualityRating(Long.parseLong(args[0]), args[1]);
                String option = "";
                for (List<InlineKeyboardButton> buttons : query.getMessage().getReplyMarkup().getKeyboard()) {
                    if (buttons.get(0).getCallbackData().equals(QUALITY_FEEDBACK_OPTIONS.getCommand() + "_" + args[0] + "_" + args[1])) {
                        option = buttons.get(0).getText();
                        break;
                    }
                }
                footerText = String.format(
                        L10n.getString("commands.callerQualityRating.execute.success", lang), option);
            } else {
                footerText = L10n.getString("commands.callerQualityRating.execute.notAllowed", lang);
            }
            edit.setText(query.getMessage().getText() + "\n\n" + footerText);
            edit.setEntities(query.getMessage().getEntities());
            edit.setReplyMarkup(null);
            bot.execute(edit);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true)
                    .text(L10n.getString("commands.callerQualityRating.execute.error", lang)).build());
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
