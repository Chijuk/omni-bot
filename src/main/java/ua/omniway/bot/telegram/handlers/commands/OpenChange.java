package ua.omniway.bot.telegram.handlers.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.formatters.ChangeFormatter;
import ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.ot.Change;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import static ua.omniway.bot.telegram.handlers.OpenObjectCallbackEnums.OPEN_CHANGE;
import static ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards.menuOnOpen;

@Slf4j
@Component
public class OpenChange implements CommandHandler {
    private final ServiceCallsApiService serviceCallsApi;
    private final ChangeFormatter formatter;

    @Autowired
    public OpenChange(ServiceCallsApiService serviceCallsApi, ChangeFormatter formatter) {
        this.serviceCallsApi = serviceCallsApi;
        this.formatter = formatter;
    }

    @Override
    public String getCommand() {
        return OPEN_CHANGE.getHandler();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = object uniqueId
     */
    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", context.getUser().getId(), this.getCommand());
        DbUser dbUser = context.getDbUser();
        String lang = dbUser.getSetting().getLanguage();
        try {
            long otUniqueId = Long.parseLong(args[0]);
            Change changeObject = serviceCallsApi.getChangeObject(otUniqueId);
            SendMessage sendMessage;
            if (changeObject != null) {
                final ServiceCallKeyboards.ButtonOptions options = new ServiceCallKeyboards.ButtonOptions();
                options.showInteraction(serviceCallsApi.canSendMessageToSpecialist(otUniqueId, dbUser.getPersonId()))
                        .showFeedback(serviceCallsApi.canSetCustomerFeedback(otUniqueId, dbUser.getPersonId()));
                sendMessage = SendMessage.builder()
                        .chatId(Long.toString(message.getChat().getId()))
                        .text(formatter.summary(changeObject, dbUser))
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(menuOnOpen(otUniqueId, lang, options))
                        .build();
            } else {
                sendMessage = SendMessage.builder()
                        .chatId(Long.toString(message.getChat().getId()))
                        .replyToMessageId(message.getMessageId())
                        .text(L10n.getString("commands.object.open.notFound", lang))
                        .parseMode(ParseMode.HTML)
                        .build();
            }
            bot.execute(sendMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(SendMessage.builder()
                    .chatId(Long.toString(message.getChat().getId()))
                    .text(L10n.getString("bot.error.general", lang))
                    .parseMode(ParseMode.HTML)
                    .build());
        }
    }
}
