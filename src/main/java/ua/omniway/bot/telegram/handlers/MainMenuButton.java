package ua.omniway.bot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.Emoji;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.MessageHandler;
import ua.omniway.bot.telegram.keyboards.InlineKeyboards;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class MainMenuButton implements MessageHandler, CommandHandler {

    @Override
    public int getRequiredAccessLevel() {
        return ActiveAction.MAIN_MENU.getId();
    }

    @Override
    public String getCommand() {
        return "/menu";
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
        final DbUser dbUser = context.getDbUser();
        if (message.getText().contains(Emoji.SCROLL.toString())) {
            execute(bot, dbUser, message);
            return true;
        }
        return false;
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception {
        execute(bot, context.getDbUser(), message);
    }

    private void execute(AbstractTelegramBot bot, DbUser dbUser, Message message) throws TelegramApiException {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {}", dbUser.getUserId(), this.getClass().getSimpleName());
        bot.execute(SendMessage.builder()
                .chatId(Long.toString(message.getChat().getId()))
                .text(L10n.getString("menu.main.text", dbUser.getSetting().getLanguage()))
                .replyMarkup(InlineKeyboards.mainMenu(dbUser.getSetting().getLanguage()))
                .build());
    }
}
