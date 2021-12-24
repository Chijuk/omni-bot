package ua.omniway.bot.telegram.handlers.menu.servicecalls;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CallbackQueryHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.utils.LogMarkers;

import java.util.Arrays;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.EDIT_SUMMARY;

@Slf4j
@Component
public class EditSummaryButton implements CallbackQueryHandler {
    private final CallbackQueryHandler addSummaryButtonHandler;

    @Autowired
    public EditSummaryButton(@Qualifier("addSummaryButton") CallbackQueryHandler addSummaryButtonHandler) {
        this.addSummaryButtonHandler = addSummaryButtonHandler;
    }

    @Override
    public String getCommand() {
        return EDIT_SUMMARY.getCommand();
    }

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    /**
     * args[0] = service call draft id
     */
    @Override
    public void onCallbackQuery(AbstractTelegramBot bot, Update update, CallbackQuery query, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} {} command", query.getMessage().getFrom().getId(), this.getCommand(), Arrays.toString(args));
        addSummaryButtonHandler.onCallbackQuery(bot, update, query, args, context);
    }
}
