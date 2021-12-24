package ua.omniway.bot.telegram;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.omniway.InitializationException;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.HandlerContext;

import javax.annotation.PreDestroy;

@Slf4j
@Component
public class TelegramBot extends AbstractTelegramBot {
    private BotSession botSession;

    @Autowired
    public TelegramBot(@Value("${bot.token}") String token, @Value("${bot.name}") String username, HandlerContext handlerContext) {
        super(token, username, handlerContext);
    }

    public void run() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            this.botSession = botsApi.registerBot(this);
        } catch (Exception e) {
            log.error("Error while starting Telegram bot", Throwables.getRootCause(e));
            throw new InitializationException("Error while starting Telegram bot", Throwables.getRootCause(e));
        }
    }

    @PreDestroy
    public void stop() {
        if (botSession != null && botSession.isRunning()) {
            log.info("--> Stopping bot session...");
            botSession.stop();
        }
    }

    @Override
    public void onClosing() {
        super.onClosing();
        log.info("--> Bot session stopped!");
    }
}
