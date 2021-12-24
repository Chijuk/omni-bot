package ua.omniway.bot.telegram.handlers.menu.servicecalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.ServiceCallDraft;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallDraftService;
import ua.omniway.utils.LogMarkers;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.NEW_COMMAND;
import static ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards.wizardKeyboard;

@Slf4j
@Component
public class NewServiceCallButton implements CallbackQueryHandler, CommandHandler {
    private final ServiceCallDraftService draftService;

    @Autowired
    public NewServiceCallButton(ServiceCallDraftService draftService) {
        this.draftService = draftService;
    }

    @Override
    public String getCommand() {
        return NEW_COMMAND.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        final DbUser dbUser = context.getDbUser();
        execute(bot, dbUser, query.getMessage());
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception {
        final DbUser dbUser = context.getDbUser();
        execute(bot, dbUser, message);
    }

    private void execute(AbstractTelegramBot bot, DbUser dbUser, Message message) throws TelegramApiException {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", message.getFrom().getId(), this.getCommand());
        final String lang = dbUser.getSetting().getLanguage();
        ServiceCallDraft draft = new ServiceCallDraft();
        draft.setUserId(dbUser.getUserId());
        draft.setWizardMessage(new Message()); // only for first save
        draft = draftService.saveDraft(draft);
        draft = draftService.findById(draft.getId());
        final Message answer = bot.execute(SendMessage.builder()
                .chatId(Long.toString(message.getChatId()))
                .text(L10n.getString("commands.newServiceCall.message.wizard", lang))
                .replyMarkup(wizardKeyboard(lang, draft))
                .parseMode(ParseMode.HTML)
                .build());
        draft.setWizardMessage(answer);
        draftService.saveDraft(draft);
    }
}
