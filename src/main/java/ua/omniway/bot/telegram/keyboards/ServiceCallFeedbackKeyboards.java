package ua.omniway.bot.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.ServiceCallFeedbackDraft;
import ua.omniway.models.rest.FeedbackOption;
import ua.omniway.models.rest.ServiceCallNotification;
import ua.omniway.services.app.L10n;

import java.util.List;
import java.util.stream.Collectors;

import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.ADD_ATTACHMENT;
import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.DELETE_ATTACHMENT;
import static ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback.FeedbackCallbackEnums.*;

public class ServiceCallFeedbackKeyboards {

    private ServiceCallFeedbackKeyboards() {
    }

    public static InlineKeyboardMarkup wizardKeyboard(String lang, ServiceCallFeedbackDraft draft) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        List<AttachmentInfo> attachments = draft.getAttachments();
        builder.addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.interactions.keyboard.editMessageText", lang))
                        .callbackData(EDIT_MESSAGE_TEXT.getCommand() + "_" + draft.getId()).build())
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.attachments.keyboard.add", lang))
                        .callbackData(ADD_ATTACHMENT.getCommand() + "_" + draft.getId()).build());
        if (attachments != null && !attachments.isEmpty()) {
            attachments = attachments.stream().filter(AttachmentInfo::isReady).collect(Collectors.toList());
            for (AttachmentInfo att : attachments) {
                builder.addRow().addButton(InlineKeyboardButton.builder()
                        .text(String.format(L10n.getString("commands.attachments.keyboard.delete", lang), att.getFileName()))
                        .callbackData(DELETE_ATTACHMENT.getCommand() + "_" + att.getId() + "_" + draft.getId()).build());
            }
        }
        builder.addRow().addButton(InlineKeyboardButton.builder()
                .text(L10n.getString("commands.interactions.keyboard.sendDraft", lang))
                .callbackData(SEND.getCommand() + "_" + draft.getId()).build());
        return builder.build();
    }

    public static InlineKeyboardMarkup feedbackOptions(ServiceCallNotification request, String lang) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder.addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.serviceCall.feedback.button.yes", lang))
                        .callbackData(FEEDBACK.getCommand() + "_" + SOLUTION_ACCEPTED.getCommand() + "_" + request.getObjectUniqueId())
                        .build())
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.serviceCall.feedback.button.no", lang))
                        .callbackData(FEEDBACK.getCommand() + "_" + SOLUTION_REJECTED.getCommand() + "_" + request.getObjectUniqueId())
                        .build())
                .build();
    }

    public static InlineKeyboardMarkup ratingOptions(long objectUniqueId, List<FeedbackOption> options) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        for (FeedbackOption option : options) {
            builder.addRow().
                    addButton(InlineKeyboardButton.builder()
                            .text(option.getValue())
                            .callbackData(QUALITY_FEEDBACK_OPTIONS.getCommand() + "_" + objectUniqueId + "_" + option.getCallback())
                            .build());
        }
        return builder.build();
    }
}
