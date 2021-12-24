package ua.omniway.bot.telegram.handlers.menu.pages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;
import java.util.List;

import static ua.omniway.bot.telegram.keyboards.InlineKeyboards.pagesMenu;
import static ua.omniway.services.app.L10n.getString;

@Slf4j
@Component
public class ServiceCallPageButton implements CallbackQueryHandler {
    private static final String IDENTIFIER = "SC";
    private final ServiceCallsCacheService callsCacheService;
    private final ServiceCallsCacheFormatter callsFormatter;

    @Autowired
    public ServiceCallPageButton(ServiceCallsCacheService callsCacheService, ServiceCallsCacheFormatter callsFormatter) {
        this.callsCacheService = callsCacheService;
        this.callsFormatter = callsFormatter;
    }

    @Override
    public String getCommand() {
        return "/openPage" + IDENTIFIER;
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = page number
     * <br>
     * args[1] = list size
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {}", query.getFrom().getId(), this.getCommand(), Arrays.toString(args));
        final DbUser dbUser = context.getDbUser();

        final EditMessageText messageText = EditMessageText.builder()
                .chatId(Long.toString(query.getMessage().getChatId()))
                .messageId(query.getMessage().getMessageId())
                .text("")                   // to not catch "text is marked non-null but is null" exception
                .parseMode(ParseMode.HTML).build();
        try {
            List<ServiceCallCache> pageData = callsCacheService.getPageData(
                    query.getMessage().getMessageId(),
                    Integer.parseInt(args[0])
            );
            if (!pageData.isEmpty()) {
                messageText.setText(callsFormatter.pages(pageData, dbUser));
                messageText.setReplyMarkup(pagesMenu(Integer.parseInt(args[0]), Integer.parseInt(args[1]), IDENTIFIER));
                // if user pushed on current page button - do nothing
                if (query.getMessage().getReplyMarkup().equals(messageText.getReplyMarkup())) return;
            } else {
                messageText.setText(getString(
                        "commands.serviceCalls.nonRelevantList", dbUser.getSetting().getLanguage()));
            }
            bot.execute(messageText);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).showAlert(true).text(
                    L10n.getString("bot.error.general", dbUser.getSetting().getLanguage())
            ).build());
        } finally {
            bot.execute(AnswerCallbackQuery.builder().callbackQueryId(query.getId()).build());
        }
    }
}
