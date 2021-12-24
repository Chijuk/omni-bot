package ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.Emoji;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.ServiceCallFeedbackDraft;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallFeedbackDraftService;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback.FeedbackCallbackEnums.SEND;
import static ua.omniway.models.converters.AttachmentInfoAttachmentMetaMapper.INSTANCE;

@Slf4j
@Component
public class FeedbackSend implements CallbackQueryHandler {
    private final ServiceCallFeedbackDraftService feedbackDraftService;
    private final ServiceCallsApiService apiService;

    @Autowired
    public FeedbackSend(ServiceCallFeedbackDraftService feedbackDraftService, ServiceCallsApiService apiService) {
        this.feedbackDraftService = feedbackDraftService;
        this.apiService = apiService;
    }

    @Override
    public String getCommand() {
        return SEND.getCommand();
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
        ServiceCallFeedbackDraft draft = feedbackDraftService.findById(Long.parseLong(args[0]));
        final DbUser dbUser = context.getDbUser();
        final String lang = dbUser.getSetting().getLanguage();
        if (draft != null) {
            try {
                List<AttachmentMeta> attachmentMeta = new ArrayList<>();
                if (!draft.getAttachments().isEmpty()) {
                    for (AttachmentInfo info : draft.getAttachments()) {
                        attachmentMeta.add(INSTANCE.toAttachmentMeta(info));
                    }
                }
                if (apiService.canSetCustomerFeedback(draft.getParentUniqueId(), dbUser.getPersonId())) {
                    apiService.setCustomerFeedbackRejected(draft.getParentUniqueId(), draft.getText(), attachmentMeta);
                    bot.execute(EditMessageText.builder()
                            .chatId(Long.toString(query.getMessage().getChat().getId()))
                            .messageId(query.getMessage().getMessageId())
                            .entities(query.getMessage().getEntities())
                            .text(query.getMessage().getText()
                                    + "\n\n"
                                    + String.format(L10n.getString("commands.serviceCall.feedback.execute.success", lang),
                                    Emoji.CROSS_MARK))
                            .build());
                    // edit main wizard message
                    if (draft.getWizardMessage() != null) {
                        bot.execute(EditMessageText.builder()
                                .chatId(Long.toString(query.getMessage().getChat().getId()))
                                .messageId(draft.getWizardMessage().getMessageId())
                                .entities(draft.getWizardMessage().getEntities())
                                .text(draft.getWizardMessage().getText()
                                        + "\n\n"
                                        + String.format(L10n.getString("commands.serviceCall.feedback.execute.success", lang),
                                        Emoji.CROSS_MARK))
                                .build()
                        );
                    }
                    // delete sent draft from DB
                    feedbackDraftService.deleteById(draft.getId());
                } else {
                    log.info("Can not set service c all feedback. Not allowed!");
                    bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true)
                            .text(L10n.getString("commands.serviceCall.feedback.execute.notAllowed", lang)).build());
                }
            } catch (OmnitrackerException e) {
                log.error(e.getMessage(), e);
                bot.execute(SendMessage.builder()
                        .chatId(Long.toString(query.getMessage().getChat().getId()))
                        .text(L10n.getString("omnitracker.error.general", lang))
                        .build());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                bot.execute(SendMessage.builder()
                        .chatId(Long.toString(query.getMessage().getChat().getId()))
                        .text(L10n.getString("commands.serviceCall.feedback.execute.error", lang))
                        .build());
            }
        } else {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.wrongReplyMessage", lang)
            ).build());
        }
    }
}
