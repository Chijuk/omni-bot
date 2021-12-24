package ua.omniway.bot.telegram.handlers.menu.interactions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.MessagingDirection;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.InteractionsDraft;
import ua.omniway.services.app.InteractionsDraftService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;

import static ua.omniway.bot.telegram.handlers.menu.interactions.InteractionsCallbackEnums.NEW_INTERACTION;

@Slf4j
@Component
public class NewInteractionButton implements CallbackQueryHandler {
    private final InteractionsDraftService interactionService;

    @Autowired
    public NewInteractionButton(InteractionsDraftService interactionService) {
        this.interactionService = interactionService;
    }

    @Override
    public String getCommand() {
        return NEW_INTERACTION.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = direction (FROM_CUSTOMER, TO_CUSTOMER)
     * <br>
     * args[1] = service call uniqueId
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        DbUser dbUser = context.getDbUser();
        String lang = dbUser.getSetting().getLanguage();
        try {
            MessagingDirection direction = MessagingDirection.getByValue(args[0]);
            InteractionsDraft draft = new InteractionsDraft();
            draft.setUserId(dbUser.getUserId())
                    .setParentUniqueId(Long.parseLong(args[1]))
                    .setDirection(direction);

            draft = interactionService.save(draft);
            draft = interactionService.findById(draft.getId());
            final Message answer = bot.execute(SendMessage.builder()
                    .chatId(Long.toString(query.getMessage().getChatId()))
                    .text(L10n.getString("commands.interactions.enterMessage", lang))
                    .replyMarkup(ForceReplyKeyboard.builder()
                            .forceReply(true)
                            .inputFieldPlaceholder(L10n.getString("commands.interactions.enterMessage", lang))
                            .build())
                    .parseMode(ParseMode.HTML)
                    .build());
            draft.setTextMessageId(answer.getMessageId());
            interactionService.save(draft);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.general", lang)
            ).build());
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}
