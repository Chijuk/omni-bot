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
import ua.omniway.bot.telegram.formatters.IncidentFormatter;
import ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.ot.Incident;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import static ua.omniway.bot.telegram.handlers.OpenObjectCallbackEnums.OPEN_INCIDENT;
import static ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards.menuOnOpen;

@Slf4j
@Component
public class OpenIncident implements CommandHandler {
    private final ServiceCallsApiService serviceCallsApi;
    private final IncidentFormatter formatter;

    @Autowired
    public OpenIncident(ServiceCallsApiService serviceCallsApi, IncidentFormatter formatter) {
        this.serviceCallsApi = serviceCallsApi;
        this.formatter = formatter;
    }

    @Override
    public String getCommand() {
        return OPEN_INCIDENT.getHandler();
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
            Incident incidentObject = serviceCallsApi.getIncidentObject(otUniqueId);
            SendMessage sendMessage;
            if (incidentObject != null) {
                final ServiceCallKeyboards.ButtonOptions options = new ServiceCallKeyboards.ButtonOptions();
                options.showInteraction(serviceCallsApi.canSendMessageToSpecialist(otUniqueId, dbUser.getPersonId()))
                        .showFeedback(serviceCallsApi.canSetCustomerFeedback(otUniqueId, dbUser.getPersonId()));
                sendMessage = SendMessage.builder()
                        .chatId(Long.toString(message.getChat().getId()))
                        .text(formatter.summary(incidentObject, dbUser))
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
