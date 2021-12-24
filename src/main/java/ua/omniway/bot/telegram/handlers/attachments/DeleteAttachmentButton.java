package ua.omniway.bot.telegram.handlers.attachments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.services.app.AttachmentInfoService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;
import java.util.List;

import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.DELETE_ATTACHMENT;

@Slf4j
@Component
public class DeleteAttachmentButton implements CallbackQueryHandler {
    private final AttachmentInfoService attachmentService;
    private final List<AttachmentListener> listeners;

    @Autowired
    public DeleteAttachmentButton(AttachmentInfoService attachmentService,
                                  @Autowired(required = false) List<AttachmentListener> listeners) {
        this.attachmentService = attachmentService;
        this.listeners = listeners;
    }

    @Override
    public String getCommand() {
        return DELETE_ATTACHMENT.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = attachment info id
     * <br>
     * args[1] = service call draft id
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        final String lang = context.getDbUser().getSetting().getLanguage();
        try {
            final AttachmentInfo attachmentInfo = attachmentService.findById(Long.parseLong(args[0]));
            if (attachmentInfo != null) {
                attachmentService.deleteInfo(Long.parseLong(args[0]));
            }
            if (listeners != null) {
                listeners.forEach(attachmentListener -> attachmentListener.onAttachmentInfoAdded(attachmentInfo, context));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.general", lang)
            ).build());
        } finally {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
        }

    }
}
