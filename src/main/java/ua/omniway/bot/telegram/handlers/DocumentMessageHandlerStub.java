package ua.omniway.bot.telegram.handlers;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DocumentMessageHandler;

@Slf4j
public class DocumentMessageHandlerStub implements DocumentMessageHandler {
    @Override
    public boolean onDocumentSent(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
        log.info("stub");
        return true;
    }
}
