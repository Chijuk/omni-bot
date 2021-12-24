package ua.omniway.bot.telegram.handlers.messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.MessageHandler;
import ua.omniway.bot.telegram.keyboards.ReplyKeyboards;
import ua.omniway.dao.exceptions.OmnitrackerException;
import ua.omniway.dao.ot.SubscribersApi;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.ot.ChatBotSetting;
import ua.omniway.models.ot.Contact;
import ua.omniway.services.app.DbUserService;
import ua.omniway.services.app.L10n;
import ua.omniway.utils.LogMarkers;

@Slf4j
@Component
public class PasswordMessage implements MessageHandler {
    private final DbUserService userService;
    private final SubscribersApi apiService;

    @Autowired
    public PasswordMessage(DbUserService userService, SubscribersApi apiService) {
        this.userService = userService;
        this.apiService = apiService;
    }

    @Override
    public boolean onMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
        final DbUser dbUser = context.getDbUser();
        if (dbUser.getActiveAction() == ActiveAction.LOGIN_PASSWORD) {
            log.info(LogMarkers.USER_ACTIVITY, "[{}] {}: {}", context.getUser().getId(), this.getClass().getSimpleName(),
                    message.getText().replaceAll(".", "*"));
            String lang = dbUser.getSetting().getLanguage();
            try {
                Long subsUniqueId = apiService.authorizeWithPassword(message.getText());
                if (subsUniqueId != null) {
                    ChatBotSetting chatBotSetting = apiService.getChatBotSetting(subsUniqueId);
                    Contact contact = apiService.getContact(chatBotSetting.getPerson().getUniqueId());
                    // Updating DbUser
                    dbUser.setPersonId(chatBotSetting.getPerson().getUniqueId());
                    dbUser.setSubscriberId(chatBotSetting.getUniqueId());
                    dbUser.setActiveAction(ActiveAction.MAIN_MENU);
                    userService.updateDbUser(dbUser);

                    String greetingMessage = L10n.getString("bot.authorization.success", lang);
                    bot.execute(SendMessage.builder()
                            .chatId(Long.toString(message.getChat().getId()))
                            .text(String.format(greetingMessage, contact.getName()))
                            .replyMarkup(ReplyKeyboards.mainMenu(lang))
                            .build());

                } else {
                    bot.execute(SendMessage.builder()
                            .chatId(Long.toString(message.getChat().getId()))
                            .text(L10n.getString("bot.authorization.fail", lang))
                            .build());
                }
            } catch (OmnitrackerException e) {
                log.error(e.getMessage(), e);
                bot.execute(SendMessage.builder()
                        .chatId(Long.toString(message.getChat().getId()))
                        .text(L10n.getString("omnitracker.error.general", lang))
                        .build());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                bot.execute(SendMessage.builder()
                        .chatId(Long.toString(message.getChat().getId()))
                        .text(L10n.getString("bot.error.general", lang))
                        .build());
            }
            return true;
        }
        return false;
    }
}
