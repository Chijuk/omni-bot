package ua.omniway.rest.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.omniway.bot.telegram.NotificationUtils;
import ua.omniway.models.db.DbUser;
import ua.omniway.models.rest.ApprovalVoteEvent;
import ua.omniway.models.rest.ApprovalVoteNotification;
import ua.omniway.models.rest.CustomerFeedbackNotification;
import ua.omniway.models.rest.ServiceCallNotification;
import ua.omniway.services.app.DbUserService;
import ua.omniway.services.exceptions.BusinessLogicException;
import ua.omniway.utils.LogMarkers;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import static ua.omniway.bot.telegram.keyboards.InlineKeyboards.approvalMenu;
import static ua.omniway.bot.telegram.keyboards.InteractionKeyboards.answerKeyboard;
import static ua.omniway.bot.telegram.keyboards.ServiceCallFeedbackKeyboards.feedbackOptions;
import static ua.omniway.bot.telegram.keyboards.ServiceCallFeedbackKeyboards.ratingOptions;
import static ua.omniway.models.rest.ServiceCallEvent.*;

@Slf4j
@RestController
@RequestMapping(path = "/notify")
public class NotifyService {
    private final DbUserService userService;
    private final NotificationUtils notificationUtils;

    @Autowired
    public NotifyService(DbUserService userService, NotificationUtils notificationUtils) {
        this.userService = userService;
        this.notificationUtils = notificationUtils;
    }

    @PostMapping(path = "/approval-votes", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> notifyUser(@Valid @RequestBody ApprovalVoteNotification request) throws BusinessLogicException, TelegramApiException {
        log.info(LogMarkers.OT_ACTIVITY, "[{}] approval vote {} notification", request.getUserUniqueId(), request.getEvent());
        DbUser dbUser = userService.getDbUserByPersonId(request.getUserUniqueId());
        if (dbUser != null) {
            InlineKeyboardMarkup keyboardMarkup = null;
            if (request.getEvent() == ApprovalVoteEvent.APPROVAL_NEW)
                keyboardMarkup = approvalMenu(request, dbUser.getSetting().getLanguage());
            notificationUtils.notify(SendMessage.builder()
                    .chatId(Long.toString(dbUser.getChatId()))
                    .replyMarkup(keyboardMarkup)
                    .text(request.getMessageBody()).build(), dbUser);
            return ResponseEntity.ok().build();
        } else {
            throw new BusinessLogicException(String.format("UniqueId %s is not DB user", request.getUserUniqueId()));
        }
    }

    @PostMapping(path = "/service-calls", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> notifyUser(@Valid @RequestBody ServiceCallNotification request) throws BusinessLogicException, TelegramApiException {
        log.info(LogMarkers.OT_ACTIVITY, "[{}] service call {} notification", request.getUserUniqueId(), request.getEvent());
        DbUser dbUser = userService.getDbUserByPersonId(request.getUserUniqueId());
        if (dbUser != null) {
            InlineKeyboardMarkup keyboardMarkup = null;
            if (request.getEvent() == SR_SOLUTION_WITH_CONFIRMATION || request.getEvent() == INC_SOLUTION_WITH_CONFIRMATION) {
                keyboardMarkup = feedbackOptions(request, dbUser.getSetting().getLanguage());
            }
            if (request.getEvent() == INTERACT_TO_CALLER || request.getEvent() == INTERACT_TO_RESPONSIBLE) {
                keyboardMarkup = answerKeyboard(request.getEvent(), request.getObjectUniqueId(), dbUser.getSetting().getLanguage());
            }
            notificationUtils.notify(SendMessage.builder()
                    .chatId(Long.toString(dbUser.getChatId()))
                    .replyMarkup(keyboardMarkup)
                    .text(request.getMessageBody()).build(), dbUser);
            return ResponseEntity.ok().build();
        } else {
            throw new BusinessLogicException(String.format("UniqueId %s is not DB user", request.getUserUniqueId()));
        }
    }

    @PostMapping(path = "/customer-feedback", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<String> notifyUser(@Valid @RequestBody CustomerFeedbackNotification request) throws BusinessLogicException, TelegramApiException {
        log.info(LogMarkers.OT_ACTIVITY, "[{}] customer feedback notification", request.getUserUniqueId());
        DbUser dbUser = userService.getDbUserByPersonId(request.getUserUniqueId());
        if (dbUser != null) {
            notificationUtils.notify(SendMessage.builder()
                    .chatId(Long.toString(dbUser.getChatId()))
                    .replyMarkup(ratingOptions(request.getObjectUniqueId(), request.getOptions()))
                    .text(request.getMessageBody()).build(), dbUser);
            return ResponseEntity.ok().build();
        } else {
            throw new BusinessLogicException(String.format("UniqueId %s is not DB user", request.getUserUniqueId()));
        }
    }
}
