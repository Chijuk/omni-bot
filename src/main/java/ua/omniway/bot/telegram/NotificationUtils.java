package ua.omniway.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.SubscribersApi;
import ua.omniway.models.db.DbUser;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class NotificationUtils {
    private final SubscribersApi api;
    private final TelegramBot bot;

    @Autowired
    public NotificationUtils(SubscribersApi api, TelegramBot bot) {
        this.api = api;
        this.bot = bot;
    }

    /**
     * Send notification to dbUser in Telegram. If user blocked the bot - deactivate user
     *
     * @param message message
     * @param user    dbUser
     * @throws TelegramApiException exception from Telegram
     */
    public void notify(SendMessage message, DbUser user) throws TelegramApiException {
        if (message.getText().length() > 4000) message.setText(message.getText().substring(0, 3995) + " ...");
        message.setParseMode(ParseMode.HTML);
        try {
            bot.execute(message);
        } catch (TelegramApiRequestException e) {
            if (e.getErrorCode() == 403) {
                processBotBlockedByUserException(user);
            }
            throw e;
        }
    }

    /**
     * Deactivate subscriber in OT using async call
     *
     * @param dbUser dbUser
     */
    private void processBotBlockedByUserException(DbUser dbUser) {
        log.info(LogMarkers.USER_ACTIVITY, "[{}] deactivating subscriber {}", dbUser.getUserId(), dbUser.getSubscriberId());
        try {
            api.deactivate(dbUser.getSubscriberId());
        } catch (OmnitrackerException e) {
            log.error("Error while blocking {}", dbUser, e);
        }
    }
}
