package ua.omniway.bot.telegram.handlers.menu.servicecalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.ServiceCallDraft;
import ua.omniway.models.ot.ProcessData;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallDraftService;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.attachmentInfoSummary;
import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.SEND;
import static ua.omniway.models.converters.AttachmentInfoAttachmentMetaMapper.INSTANCE;

@Slf4j
@Component
public class NewServiceCallSend implements CallbackQueryHandler {
    private final ServiceCallsApiService apiService;
    private final ServiceCallDraftService draftService;

    @Autowired
    public NewServiceCallSend(ServiceCallsApiService apiService, ServiceCallDraftService draftService) {
        this.apiService = apiService;
        this.draftService = draftService;
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
     * args[0] = service call draft id
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        ServiceCallDraft draft = draftService.findById(Long.parseLong(args[0]));
        DbUser dbUser = context.getDbUser();
        String lang = dbUser.getSetting().getLanguage();
        if (draft != null) {
            try {
                List<AttachmentMeta> attachmentMeta = new ArrayList<>();
                if (!draft.getAttachments().isEmpty()) {
                    for (AttachmentInfo info : draft.getAttachments()) {
                        attachmentMeta.add(INSTANCE.toAttachmentMeta(info));
                    }
                }
                long objectUniqueId = apiService.createGeneral(dbUser.getPersonId(), draft.getSummary(), null, attachmentMeta);
                ProcessData processData = apiService.getProcessDataObject(objectUniqueId);
                bot.execute(EditMessageText.builder()
                        .chatId(Long.toString(query.getMessage().getChat().getId()))
                        .messageId(query.getMessage().getMessageId())
                        .text(
                                String.format(L10n.getString("commands.newServiceCall.message.ready.main", lang),
                                        draft.getSummary()) +
                                        "\n" +
                                        attachmentInfoSummary(lang, draft.getAttachments()) +
                                        "\n\n" +
                                        String.format(L10n.getString("commands.newServiceCall.message.success", lang),
                                                processData.getId()))
                        .parseMode(ParseMode.HTML)
                        .build());
                // delete main wizard message
                if (draft.getWizardMessage() != null) {
                    bot.execute(DeleteMessage.builder()
                            .chatId(Long.toString(query.getMessage().getChat().getId()))
                            .messageId(draft.getWizardMessage().getMessageId())
                            .build()
                    );
                }
                // delete sent draft from DB
                draftService.deleteById(draft.getId());
            } catch (OmnitrackerException e) {
                log.error(e.getMessage(), e);
                bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                        L10n.getString("omnitracker.error.general", lang)
                ).build());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                        L10n.getString("bot.error.general", lang)
                ).build());
            }
        } else {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.wrongReplyMessage", lang)
            ).build());
        }
    }
}
