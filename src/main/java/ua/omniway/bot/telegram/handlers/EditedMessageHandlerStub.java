package ua.omniway.bot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.EditedMessageHandler;

@Slf4j
@Component
public class EditedMessageHandlerStub implements EditedMessageHandler {
    @Override
    public boolean onEditMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
        log.info("stub");
        return true;
    }
}
