package ua.omniway.bot.telegram.handlers.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.CommandHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class HelpCommand implements CommandHandler {
    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public void onCommandMessage(AbstractTelegramBot bot, Update update, Message message, String[] args, DefaultDbContext context) throws Exception {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] {} command", context.getUser().getId(), this.getCommand());
        bot.execute(SendMessage.builder()
                .chatId(Long.toString(message.getChat().getId()))
                .text(L10n.getString("commands.help.defaultText", context.getDbUser().getSetting().getLanguage()))
                .build());
    }
}
