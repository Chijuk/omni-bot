package ua.omniway.bot.telegram.handlers.menu.approvals;

import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.ot.ApprovalRequest;
import ua.omniway.models.ot.ApprovalResultEnum;
import ua.omniway.services.app.ApprovalsApiService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
public class ApprovalResultHandler implements CallbackQueryHandler {
    private final ApprovalsApiService approvalsApiService;

    @Autowired
    public ApprovalResultHandler(ApprovalsApiService approvalsApiService) {
        this.approvalsApiService = approvalsApiService;
    }

    @Override
    public String getCommand() {
        return "/approvalVote";
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = approved/rejected
     * <br>
     * args[1] = approval vote uniqueId
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        String lang = context.getDbUser().getSetting().getLanguage();
        try {
            if (approvalsApiService.canSetResult(Long.parseLong(args[1]), context.getDbUser().getPersonId())) {
                if (ApprovalResultEnum.getByAlias(args[0]) == ApprovalResultEnum.APPROVED) {
                    approvalsApiService.setResultApproved(Long.parseLong(args[1]));
                } else {
                    approvalsApiService.setResultRejected(Long.parseLong(args[1]));
                }
                EditMessageText edit = new EditMessageText();
                edit.setChatId(Long.toString(query.getMessage().getChat().getId()));
                edit.setMessageId(query.getMessage().getMessageId());
                edit.setText(query.getMessage().getText() + "\n\n" +
                        String.format(L10n.getString("commands.approvalVote.execute.success", lang),
                                ApprovalResultEnum.getByAlias(args[0]).getEmoji()) + " "
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                edit.setEntities(query.getMessage().getEntities());
                edit.setReplyMarkup(null);
                bot.execute(edit);
            } else {
                ApprovalRequest approvalRequest = approvalsApiService.getApprovalRequest(Long.parseLong(args[1]));
                if (approvalRequest.getApprovalResult() != null) {
                    EditMessageText edit = new EditMessageText();
                    edit.setChatId(Long.toString(query.getMessage().getChat().getId()));
                    edit.setMessageId(query.getMessage().getMessageId());
                    LocalDateTime completionDate;
                    if (approvalRequest.getDateApproved() != null) {
                        completionDate = approvalRequest.getDateApproved();
                    } else {
                        completionDate = approvalRequest.getDateClosed();
                    }
                    String approvalResultMeta = L10n.getString("omnitracker.meta.approvalRequest.approvalResult", lang);
                    Map<String, String> approvalResultMetaMap = Splitter.on(",").withKeyValueSeparator("=").split(approvalResultMeta);
                    String localizedApprovalResultMetaMap = approvalResultMetaMap.get(approvalRequest.getApprovalResult().getAlias());
                    edit.setText(query.getMessage().getText() + "\n\n" +
                            String.format(L10n.getString("commands.approvalVote.execute.done", lang),
                                    completionDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                                    approvalRequest.getApprovalResult().getEmoji() + " " + localizedApprovalResultMetaMap
                            ));
                    edit.setEntities(query.getMessage().getEntities());
                    edit.setReplyMarkup(null);
                    bot.execute(edit);
                } else {
                    bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true)
                            .text(L10n.getString("commands.callerQualityRating.execute.notAllowed", lang))
                            .build());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true)
                    .text(L10n.getString("commands.approvalVote.execute.error", lang)).build());
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
