package ua.omniway.bot.telegram.handlers.attachments;

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
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.AttachmentInfoService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;

import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.ADD_ATTACHMENT;

@Slf4j
@Component
public class AddAttachmentButton implements CallbackQueryHandler {
    private final AttachmentInfoService attachmentService;

    @Autowired
    public AddAttachmentButton(AttachmentInfoService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Override
    public String getCommand() {
        return ADD_ATTACHMENT.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = parent object id
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        DbUser dbUser = context.getDbUser();
        String lang = dbUser.getSetting().getLanguage();
        try {
            if (attachmentService.parentObjectExists(Long.parseLong(args[0]))) {
                AttachmentInfo attachment = new AttachmentInfo();
                attachment.setUserId(dbUser.getUserId());
                attachment.setParentId(Long.parseLong(args[0]));
                final Message answer = bot.execute(SendMessage.builder()
                        .chatId(Long.toString(query.getMessage().getChatId()))
                        .text(L10n.getString("commands.attachments.message.add", lang))
                        .replyMarkup(ForceReplyKeyboard.builder()
                                .forceReply(true)
                                .inputFieldPlaceholder(
                                        L10n.getString("commands.attachments.inputPlaceholder", lang))
                                .build())
                        .build());
                attachment.setReplyMessageId(answer.getMessageId());
                attachmentService.saveInfo(attachment);
                bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
            } else {
                bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                        L10n.getString("bot.error.wrongReplyMessage", lang)
                ).build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.general", lang)
            ).build());
        }
    }
}
