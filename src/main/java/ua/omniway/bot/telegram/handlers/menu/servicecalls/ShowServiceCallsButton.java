package ua.omniway.bot.telegram.handlers.menu.servicecalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.formatters.ServiceCallsCacheFormatter;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.db.ServiceCallCache;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsCacheService;
import ua.omniway.services.exceptions.ServiceException;
import ua.omniway.utils.LogMarkers;

import java.util.List;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.SHOW_SC;
import static ua.omniway.bot.telegram.keyboards.InlineKeyboards.mainMenu;
import static ua.omniway.bot.telegram.keyboards.InlineKeyboards.pagesMenu;

@Slf4j
@Component
public class ShowServiceCallsButton implements CallbackQueryHandler {
    private static final String IDENTIFIER = "SC";
    private final ServiceCallsCacheService serviceCallsCacheService;
    private final ServiceCallsCacheFormatter callsFormatter;

    @Autowired
    public ShowServiceCallsButton(ServiceCallsCacheService serviceCallsCacheService, ServiceCallsCacheFormatter callsFormatter) {
        this.serviceCallsCacheService = serviceCallsCacheService;
        this.callsFormatter = callsFormatter;
    }

    @Override
    public String getCommand() {
        return SHOW_SC.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", query.getFrom().getId(), this.getCommand());
        DbUser dbUser = context.getDbUser();
        if (args.length > 0) {
            switch (args[0]) {
                case "back":
                    bot.execute(EditMessageText.builder()
                            .chatId(Long.toString(query.getMessage().getChat().getId()))
                            .messageId(query.getMessage().getMessageId())
                            .text(L10n.getString("menu.main.text", dbUser.getSetting().getLanguage()))
                            .replyMarkup(mainMenu(dbUser.getSetting().getLanguage()))
                            .build());
                    break;
                case "caller":
                    try {
                        List<ServiceCallCache> calls = serviceCallsCacheService.getActualServiceCalls(
                                dbUser.getPersonId(),
                                dbUser.getUserId()
                        );
                        if (!calls.isEmpty()) {
                            Message answer = bot.execute(SendMessage.builder()
                                    .chatId(Long.toString(query.getMessage().getChat().getId()))
                                    .parseMode(ParseMode.HTML)
                                    .text(callsFormatter.pages(calls, dbUser))
                                    .replyMarkup(pagesMenu(1, calls.size(), IDENTIFIER))
                                    .build()
                            );
                            calls.forEach(sc -> sc.setMessageId(answer.getMessageId()));        // set answer messageId to cache list
                            serviceCallsCacheService.updateCallerCache(dbUser.getUserId(), dbUser.getPersonId(), calls);               // update cache in DB
                        } else {
                            bot.execute(AnswerCallbackQuery.builder()
                                    .callbackQueryId(query.getId())
                                    .text(L10n.getString("commands.serviceCalls.emptyList", dbUser.getSetting().getLanguage()))
                                    .showAlert(true)
                                    .build());
                        }
                    } catch (ServiceException e) {
                        log.error(e.getMessage(), e);
                        bot.execute(AnswerCallbackQuery.builder()
                                .callbackQueryId(query.getId())
                                .text(L10n.getString("omnitracker.error.general", dbUser.getSetting().getLanguage()))
                                .showAlert(true)
                                .build());
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        bot.execute(AnswerCallbackQuery.builder()
                                .callbackQueryId(query.getId())
                                .text(L10n.getString("bot.error.general", dbUser.getSetting().getLanguage()))
                                .showAlert(true)
                                .build());
                    }
                    break;
                default:
                    log.error("Unknown parameter in ShowServiceCallsButton callback query {}", args[0]);
            }
        }
        bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
    }
}