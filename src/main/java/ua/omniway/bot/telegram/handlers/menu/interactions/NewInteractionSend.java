package ua.omniway.bot.telegram.handlers.menu.interactions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.AttachmentMeta;
import ua.omniway.models.MessagingDirection;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.InteractionsDraft;
import ua.omniway.services.app.InteractionsDraftService;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.services.app.WorkOrdersApiService;
import ua.omniway.utils.LogMarkers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ua.omniway.bot.telegram.formatters.AttachmentInfoMessageBuilder.attachmentInfoSummary;
import static ua.omniway.bot.telegram.handlers.menu.interactions.InteractionsCallbackEnums.SEND;
import static ua.omniway.models.converters.AttachmentInfoAttachmentMetaMapper.INSTANCE;

@Slf4j
@Component
public class NewInteractionSend implements CallbackQueryHandler {
    private final InteractionsDraftService draftService;
    private final ServiceCallsApiService callsApiService;
    private final WorkOrdersApiService ordersApiService;

    @Autowired
    public NewInteractionSend(InteractionsDraftService draftService, ServiceCallsApiService callsApiService,
                              WorkOrdersApiService ordersApiService) {
        this.draftService = draftService;
        this.callsApiService = callsApiService;
        this.ordersApiService = ordersApiService;
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
     * args[0] = interaction draft id
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        InteractionsDraft draft = draftService.findById(Long.parseLong(args[0]));
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
                boolean processed = false;
                final MessagingDirection direction = draft.getDirection();
                switch (direction) {
                    case FROM_CUSTOMER:
                        if (callsApiService.canSendMessageToSpecialist(draft.getParentUniqueId(), dbUser.getPersonId())) {
                            callsApiService.sendMessageToSpecialist(draft.getParentUniqueId(), draft.getText(), attachmentMeta);
                            processed = true;
                        } else {
                            bot.execute(SendMessage.builder().chatId(Long.toString(query.getMessage().getChatId()))
                                    .text(L10n.getString("commands.interactions.send.validationFail", lang))
                                    .build());
                        }
                        break;
                    case TO_CUSTOMER:
                        if (ordersApiService.canSendMessageToCustomer(draft.getParentUniqueId(), dbUser.getPersonId())) {
                            ordersApiService.sendMessageToCustomer(draft.getParentUniqueId(), draft.getText(), attachmentMeta);
                            processed = true;
                        } else {
                            bot.execute(SendMessage.builder().chatId(Long.toString(query.getMessage().getChatId()))
                                    .text(L10n.getString("commands.interactions.send.validationFail", lang))
                                    .build());
                        }
                        break;
                    default:
                        log.error("Unknown direction: {}", direction.getValue());
                }
                if (processed) {
                    bot.execute(EditMessageText.builder()
                            .chatId(Long.toString(query.getMessage().getChat().getId()))
                            .messageId(query.getMessage().getMessageId())
                            .text(
                                    String.format(L10n.getString("commands.interactions.message.ready.main", lang),
                                            draft.getText()) +
                                            "\n" +
                                            attachmentInfoSummary(lang, draft.getAttachments()) +
                                            "\n\n" +
                                            String.format(L10n.getString("commands.interactions.message.success", lang),
                                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))))
                            .parseMode(ParseMode.HTML)
                            .build());
                    draftService.deleteById(draft.getId());
                }
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
