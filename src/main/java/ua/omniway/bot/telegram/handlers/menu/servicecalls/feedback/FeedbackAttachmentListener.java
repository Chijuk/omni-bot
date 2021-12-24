package ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.TelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.handlers.attachments.AttachmentListener;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.ServiceCallFeedbackDraft;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallFeedbackDraftService;

import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.attachmentInfoSummary;
import static ua.omniway.bot.telegram.keyboards.ServiceCallFeedbackKeyboards.wizardKeyboard;

@Slf4j
@Component
public class FeedbackAttachmentListener implements AttachmentListener {
    private final TelegramBot bot;
    private final ServiceCallFeedbackDraftService feedbackDraftService;

    @Autowired
    public FeedbackAttachmentListener(TelegramBot bot, ServiceCallFeedbackDraftService feedbackDraftService) {
        this.bot = bot;
        this.feedbackDraftService = feedbackDraftService;
    }

    @Override
    public void onAttachmentInfoAdded(AttachmentInfo attachmentInfo, DefaultDbContext context) {
        final ServiceCallFeedbackDraft draft = feedbackDraftService.findById(attachmentInfo.getParentId());
        if (draft != null) {
            log.info("Attachment OID {} processed by InteractionsAttachmentListener", attachmentInfo.getOid());
            final String lang = context.getDbUser().getSetting().getLanguage();
            try {
                Message answer = bot.execute(SendMessage.builder()
                        .chatId(String.valueOf(context.getDbUser().getChatId()))
                        .text(
                                String.format(L10n.getString("commands.serviceCall.feedback.message.ready.main", lang), draft.getText()) +
                                        "\n" +
                                        attachmentInfoSummary(lang, draft.getAttachments()) +
                                        "\n\n" +
                                        L10n.getString("commands.interactions.tip1", lang)
                        )
                        .replyMarkup(wizardKeyboard(lang, draft))
                        .parseMode(ParseMode.HTML)
                        .build());
                // delete old send message if exists
                bot.execute(DeleteMessage.builder()
                        .chatId(Long.toString(draft.getSendMessage().getChat().getId()))
                        .messageId(draft.getSendMessage().getMessageId())
                        .build()
                );
                draft.setSendMessage(answer);
                feedbackDraftService.save(draft);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                try {
                    bot.execute(SendMessage.builder().chatId(Long.toString(context.getDbUser().getChatId()))
                            .text(L10n.getString("bot.error.general", lang))
                            .build());
                } catch (TelegramApiException ex) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void onAttachmentInfoDeleted(AttachmentInfo attachmentInfo, Message message, DefaultDbContext context) {
        final ServiceCallFeedbackDraft draft = feedbackDraftService.findById(attachmentInfo.getParentId());
        if (draft != null) {
            log.info("Attachment OID {} processed by ServiceCallAttachmentListener", attachmentInfo.getOid());
            final String lang = context.getDbUser().getSetting().getLanguage();
            try {
                bot.execute(EditMessageText.builder()
                        .chatId(String.valueOf(context.getDbUser().getChatId()))
                        .messageId(message.getMessageId())
                        .text(message.getText())
                        .entities(message.getEntities())
                        .replyMarkup(wizardKeyboard(lang, draft))
                        .build());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                try {
                    bot.execute(SendMessage.builder().chatId(Long.toString(context.getDbUser().getChatId()))
                            .text(L10n.getString("bot.error.general", lang))
                            .build());
                } catch (TelegramApiException ex) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
