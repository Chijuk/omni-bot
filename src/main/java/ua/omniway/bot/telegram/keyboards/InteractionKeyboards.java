package ua.omniway.bot.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.InteractionsDraft;
import ua.omniway.models.rest.ServiceCallEvent;
import ua.omniway.services.app.L10n;

import java.util.List;
import java.util.stream.Collectors;

import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.ADD_ATTACHMENT;
import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.DELETE_ATTACHMENT;
import static ua.omniway.bot.telegram.handlers.menu.interactions.InteractionsCallbackEnums.*;
import static ua.omniway.models.MessagingDirection.FROM_CUSTOMER;
import static ua.omniway.models.MessagingDirection.TO_CUSTOMER;
import static ua.omniway.models.rest.ServiceCallEvent.INTERACT_TO_CALLER;

public class InteractionKeyboards {

    private InteractionKeyboards() {
        //class with static methods
    }

    public static InlineKeyboardMarkup wizardKeyboard(String lang, InteractionsDraft draft) {
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

    public static InlineKeyboardMarkup answerKeyboard(ServiceCallEvent event, long objectUniqueId, String lang) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        if (event == INTERACT_TO_CALLER) {
            builder.addRow()
                    .addButton(InlineKeyboardButton.builder()
                            .text(L10n.getString("commands.serviceCall.sendInteraction.toResponsible", lang))
                            .callbackData(NEW_INTERACTION.getCommand() + "_"
                                    + FROM_CUSTOMER.getValue() + "_" // FROM_CUSTOMER - to responsible
                                    + objectUniqueId).build());
        } else {
            builder.addRow()
                    .addButton(InlineKeyboardButton.builder()
                            .text(L10n.getString("commands.serviceCall.sendInteraction.toCustomer", lang))
                            .callbackData(NEW_INTERACTION.getCommand() + "_"
                                    + TO_CUSTOMER.getValue() + "_" // TO_CUSTOMER - to customer
                                    + objectUniqueId).build());
        }
        return builder.build();
    }
}
