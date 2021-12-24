package ua.omniway.bot.telegram.handlers.menu.servicecalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.formatters.ProcessDataFormatter;
import ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.ot.ProcessData;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsApiService;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.SHOW_INFO;
import static ua.omniway.bot.telegram.keyboards.ServiceCallKeyboards.menuOnOpen;

@Slf4j
@Component
public class ShowLessMoreHandler implements CallbackQueryHandler {
    private final ServiceCallsApiService callsApiService;
    private final ProcessDataFormatter formatter;

    @Autowired
    public ShowLessMoreHandler(ServiceCallsApiService callsApiService,
                               @Qualifier("processDataFormatter") ProcessDataFormatter formatter) {
        this.callsApiService = callsApiService;
        this.formatter = formatter;
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public String getCommand() {
        return SHOW_INFO.getCommand();
    }

    /**
     * args[0] = less or more
     * <br>
     * args[1] = service call uniqueId
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        DbUser dbUser = context.getDbUser();
        String lang = dbUser.getSetting().getLanguage();
        try {
            long otUniqueId = Long.parseLong(args[1]);
            ProcessData processData = callsApiService.getProcessDataObject(otUniqueId);
            if (processData == null) {
                bot.execute(SendMessage.builder()
                        .chatId(Long.toString(query.getMessage().getChat().getId()))
                        .replyToMessageId(query.getMessage().getMessageId())
                        .text(L10n.getString("commands.object.open.notFound", lang))
                        .parseMode(ParseMode.HTML)
                        .build());
            } else {
                String text;
                InlineKeyboardMarkup keyboardMarkup;
                final ServiceCallKeyboards.ButtonOptions options = new ServiceCallKeyboards.ButtonOptions();
                options.showInteraction(callsApiService.canSendMessageToSpecialist(otUniqueId, dbUser.getPersonId()))
                        .showFeedback(callsApiService.canSetCustomerFeedback(otUniqueId, dbUser.getPersonId()));
                if ("more".equals(args[0])) {
                    text = formatter.detailed(processData, dbUser);
                    options.showInfoLess(true);
                } else {
                    text = formatter.summary(processData, dbUser);
                }
                keyboardMarkup = menuOnOpen(otUniqueId, lang, options);
                bot.execute(EditMessageText.builder()
                        .chatId(Long.toString(query.getMessage().getChat().getId()))
                        .messageId(query.getMessage().getMessageId())
                        .text(text)
                        .parseMode(ParseMode.HTML)
                        .replyMarkup(keyboardMarkup)
                        .build());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(SendMessage.builder()
                    .chatId(Long.toString(query.getMessage().getChat().getId()))
                    .text(L10n.getString("bot.error.general", lang))
                    .parseMode(ParseMode.HTML)
                    .build());
        } finally {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
        }
    }
}
