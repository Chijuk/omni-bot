package ua.omniway.bot.telegram.handlers.menu.servicecalls;

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
import ua.omniway.models.db.ServiceCallDraft;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallDraftService;

import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.attachmentInfoSummary;
import static ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards.wizardKeyboard;

@Slf4j
@Component
public class ServiceCallAttachmentListener implements AttachmentListener {
    private final TelegramBot bot;
    private final ServiceCallDraftService draftService;

    @Autowired
    public ServiceCallAttachmentListener(TelegramBot bot, ServiceCallDraftService draftService) {
        this.bot = bot;
        this.draftService = draftService;
    }

    @Override
    public void onAttachmentInfoAdded(AttachmentInfo attachmentInfo, DefaultDbContext context) {
        final ServiceCallDraft draft = draftService.findById(attachmentInfo.getParentId());
        if (draft != null) {
            log.info("Attachment OID {} processed by ServiceCallAttachmentListener", attachmentInfo.getOid());
            final String lang = context.getDbUser().getSetting().getLanguage();
            try {
                Message answer = bot.execute(SendMessage.builder()
                        .chatId(String.valueOf(context.getDbUser().getChatId()))
                        .text(
                                String.format(L10n.getString("commands.newServiceCall.message.ready.main", lang), draft.getSummary()) +
                                        "\n" +
                                        attachmentInfoSummary(lang, draft.getAttachments()) +
                                        "\n\n" +
                                        L10n.getString("commands.newServiceCall.tip1", lang)
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
                draftService.saveDraft(draft);
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
        final ServiceCallDraft draft = draftService.findById(attachmentInfo.getParentId());
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
