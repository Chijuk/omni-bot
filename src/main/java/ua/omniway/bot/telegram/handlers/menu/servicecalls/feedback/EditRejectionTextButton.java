package ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.ServiceCallFeedbackDraft;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallFeedbackDraftService;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback.FeedbackCallbackEnums.EDIT_MESSAGE_TEXT;

@Slf4j
@Component
public class EditRejectionTextButton implements CallbackQueryHandler {
    private final ServiceCallFeedbackDraftService feedbackDraftService;

    @Autowired
    public EditRejectionTextButton(ServiceCallFeedbackDraftService feedbackDraftService) {
        this.feedbackDraftService = feedbackDraftService;
    }

    @Override
    public String getCommand() {
        return EDIT_MESSAGE_TEXT.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = service call feedback draft id
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        final String lang = context.getDbUser().getSetting().getLanguage();
        final ServiceCallFeedbackDraft draft = feedbackDraftService.findById(Long.parseLong(args[0]));
        if (draft != null) {
            final Message answer = bot.execute(SendMessage.builder()
                    .chatId(Long.toString(query.getMessage().getChatId()))
                    .text(L10n.getString("commands.interactions.enterMessage", lang))
                    .replyMarkup(ForceReplyKeyboard.builder()
                            .forceReply(true)
                            .inputFieldPlaceholder(
                                    L10n.getString("commands.interactions.enterMessage", lang))
                            .build())
                    .build());
            draft.setTextMessageId(answer.getMessageId());
            feedbackDraftService.save(draft);
        } else {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.wrongReplyMessage", lang)
            ).build());
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
