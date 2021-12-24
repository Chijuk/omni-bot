package ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import ua.omniway.bot.Emoji;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.ServiceCallFeedbackDraft;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallFeedbackDraftService;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback.FeedbackCallbackEnums.*;

@Slf4j
@Component
public class FeedbackHandler implements CallbackQueryHandler {
    private final ServiceCallsApiService apiService;
    private final ServiceCallFeedbackDraftService feedbackDraftService;

    @Autowired
    public FeedbackHandler(ServiceCallsApiService apiService, ServiceCallFeedbackDraftService feedbackDraftService) {
        this.apiService = apiService;
        this.feedbackDraftService = feedbackDraftService;
    }

    @Override
    public String getCommand() {
        return FEEDBACK.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = result (SOLUTION_ACCEPTED, SOLUTION_REJECTED)
     * <br>
     * args[1] = service call uniqueId
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        final DbUser dbUser = context.getDbUser();
        final String lang = dbUser.getSetting().getLanguage();
        FeedbackCallbackEnums feedback = byValue(args[0]);
        try {
            if (feedback == SOLUTION_ACCEPTED) {
                if (apiService.canSetCustomerFeedback(Long.parseLong(args[1]), dbUser.getPersonId())) {
                    apiService.setCustomerFeedbackAccepted(Long.parseLong(args[1]));
                    bot.execute(EditMessageText.builder()
                            .chatId(Long.toString(query.getMessage().getChat().getId()))
                            .messageId(query.getMessage().getMessageId())
                            .entities(query.getMessage().getEntities())
                            .text(query.getMessage().getText()
                                    + "\n\n"
                                    + String.format(L10n.getString("commands.serviceCall.feedback.execute.success", lang),
                                    Emoji.WHITE_CHECK_MARK))
                            .build());
                } else {
                    log.info("Can not set service c all feedback. Not allowed!");
                    bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true)
                            .text(L10n.getString("commands.serviceCall.feedback.execute.notAllowed", lang)).build());
                }
            }
            if (feedback == SOLUTION_REJECTED) {
                ServiceCallFeedbackDraft draft = new ServiceCallFeedbackDraft();
                draft.setUserId(dbUser.getUserId())
                        .setParentUniqueId(Long.parseLong(args[1]))
                        .setWizardMessage(query.getMessage());
                draft = feedbackDraftService.save(draft);
                draft = feedbackDraftService.findById(draft.getId());
                final Message answer = bot.execute(SendMessage.builder()
                        .chatId(Long.toString(query.getMessage().getChatId()))
                        .text(L10n.getString("commands.serviceCall.feedback.message.rejectReason", lang))
                        .replyMarkup(ForceReplyKeyboard.builder()
                                .forceReply(true)
                                .inputFieldPlaceholder(L10n.getString("commands.serviceCall.feedback.message.rejectReason", lang))
                                .build())
                        .parseMode(ParseMode.HTML)
                        .build());
                draft.setTextMessageId(answer.getMessageId());
                feedbackDraftService.save(draft);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true)
                    .text(L10n.getString("commands.serviceCall.feedback.execute.error", lang))
                    .build());
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
