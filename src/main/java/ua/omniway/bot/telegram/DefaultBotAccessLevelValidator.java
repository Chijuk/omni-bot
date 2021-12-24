package ua.omniway.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.bothandlerapi.handlers.AccessLevelValidator;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.services.app.L10n;

@Slf4j
public class DefaultBotAccessLevelValidator implements AccessLevelValidator {

    @Override
    public boolean validate(DefaultDbContext context) {
        return context.getDbUser().isActive();
    }

    @Override
    public void onValidationError(DefaultDbContext context, AbsSender sender) {
        log.debug("Validation failed for user: {}", context.getUser().getUserName());
        try {
            sender.execute(SendMessage.builder()
                    .chatId(Long.toString(context.getUser().getId()))
                    .text(L10n.getString("bot.error.accessDeny", context.getDbUser().getSetting().getLanguage()))
                    .build());
        } catch (TelegramApiException e) {
            log.error("Error sending message", e);
        }
    }
}