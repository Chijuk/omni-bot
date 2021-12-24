package ua.omniway.bot.telegram.handlers.messages;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.MessageHandler;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.ApprovalVoteTemp;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.ApprovalVoteTempService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

/**
 * May be used to process rejection comment in approval request.
 * Also needs to save approval vote temp object in ApprovalResultHandler class
 * For now is not on use
 */
@Slf4j
public class ApprovalRejectionMessage implements MessageHandler {
    private final ApprovalVoteTempService voteTempService;

    public ApprovalRejectionMessage(ApprovalVoteTempService voteTempService) {
        this.voteTempService = voteTempService;
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
        final DbUser dbUser = context.getDbUser();
        if (dbUser.getActiveAction() == ActiveAction.APPROVAL_REJECT) {
            log.info(LogMarkers.USER_ACTIVITY, "[{}] {}: {}", context.getUser().getId(), this.getClass().getSimpleName(), message.getText());
            final ApprovalVoteTemp approvalVoteTemp = voteTempService.findById(dbUser.getUserId());
            if (approvalVoteTemp == null) {
                log.error("Approval vote temp for {} is null", dbUser.getUserId());
                bot.execute(SendMessage.builder().chatId(Long.toString(message.getChatId()))
                        .text(L10n.getString("commands.approvalVote.execute.error", dbUser.getSetting().getLanguage())).build());
            } else {
                try {
                    // enter logic here
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    bot.execute(SendMessage.builder().chatId(Long.toString(message.getChatId()))
                            .text(L10n.getString("commands.approvalVote.execute.error", dbUser.getSetting().getLanguage())).build());
                }
            }
            return true;
        }
        return false;
    }
}
