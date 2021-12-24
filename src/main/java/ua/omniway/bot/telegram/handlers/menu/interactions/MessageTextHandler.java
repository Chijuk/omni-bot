package ua.omniway.bot.telegram.handlers.menu.interactions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.MessageHandler;
import ua.omniway.bot.telegram.keyboards.InteractionKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.InteractionsDraft;
import ua.omniway.services.app.InteractionsDraftService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.attachmentInfoSummary;

@Slf4j
@Component
public class MessageTextHandler implements MessageHandler {
    private final InteractionsDraftService draftService;

    @Autowired
    public MessageTextHandler(InteractionsDraftService draftService) {
        this.draftService = draftService;
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
        if (message.isReply()) {
            final InteractionsDraft draft = draftService.findByTextMessageId(message.getReplyToMessage().getMessageId());
            if (draft != null) {
                log.info(LogMarkers.USER_ACTIVITY, "[{}] {}: {}", context.getUser().getId(), this.getClass().getSimpleName(), message.getText());
                final String lang = context.getDbUser().getSetting().getLanguage();
                draft.setText(message.getText());
                try {
                    // delete old send message if exists
                    if (draft.getSendMessage() != null) {
                        bot.execute(DeleteMessage.builder()
                                .chatId(Long.toString(message.getChat().getId()))
                                .messageId(draft.getSendMessage().getMessageId())
                                .build()
                        );
                    }
                    // send new send message
                    Message answer = bot.execute(SendMessage.builder()
                            .chatId(String.valueOf(message.getChatId()))
                            .text(
                                    String.format(L10n.getString("commands.interactions.message.ready.main", lang), draft.getText()) +
                                            "\n" +
                                            attachmentInfoSummary(lang, draft.getAttachments()) +
                                            "\n\n" +
                                            L10n.getString("commands.interactions.tip1", lang)
                            )
                            .replyMarkup(InteractionKeyboards.wizardKeyboard(lang, draft))
                            .parseMode(ParseMode.HTML)
                            .build());
                    draft.setSendMessage(answer);
                    draftService.save(draft);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    bot.execute(SendMessage.builder().chatId(Long.toString(message.getChatId()))
                            .text(L10n.getString("bot.error.general", lang))
                            .build());
                }
                return true;
            }
        }
        return false;
    }
}
