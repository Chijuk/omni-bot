package ua.omniway.bot.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.omniway.models.db.AttachmentInfo;
import ua.omniway.models.db.ServiceCallDraft;
import ua.omniway.services.app.L10n;

import java.util.List;
import java.util.stream.Collectors;

import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.ADD_ATTACHMENT;
import static ua.omniway.bot.telegram.handlers.attachments.AttachmentsCallbackEnums.DELETE_ATTACHMENT;
import static ua.omniway.bot.telegram.handlers.menu.interactions.InteractionsCallbackEnums.NEW_INTERACTION;
import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.SEND;
import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.*;
import static ua.omniway.bot.telegram.handlers.menu.servicecalls.feedback.FeedbackCallbackEnums.*;
import static ua.omniway.models.MessagingDirection.FROM_CUSTOMER;

public class ServiceCallKeyboards {

    private ServiceCallKeyboards() {
        //class with static methods
    }

    public static InlineKeyboardMarkup wizardKeyboard(String lang, ServiceCallDraft draft) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        List<AttachmentInfo> attachments = draft.getAttachments();
        if (draft.getSummary() == null) {
            builder.addRow().addButton(InlineKeyboardButton.builder()
                    .text(L10n.getString("commands.newServiceCall.keyboard.addSummary", lang))
                    .callbackData(ADD_SUMMARY.getCommand() + "_" + draft.getId()).build());
        } else {
            builder.addRow()
                    .addButton(InlineKeyboardButton.builder()
                            .text(L10n.getString("commands.newServiceCall.keyboard.editSummary", lang))
                            .callbackData(EDIT_SUMMARY.getCommand() + "_" + draft.getId()).build())
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
                    .text(L10n.getString("commands.newServiceCall.keyboard.sendDraft", lang))
                    .callbackData(SEND.getCommand() + "_" + draft.getId()).build());
        }
        return builder.build();
    }

    public static InlineKeyboardMarkup serviceCallInteractionMenu(long objectUniqueId, String language) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder.addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.serviceCall.showMore", language))
                        .callbackData(SHOW_INFO.getCommand() + "_" + "more" + "_" + objectUniqueId).build())
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.serviceCall.sendInteraction.toResponsible", language))
                        .callbackData(NEW_INTERACTION.getCommand() + "_"
                                + FROM_CUSTOMER.getValue() + "_" // FROM_CUSTOMER - to responsible
                                + objectUniqueId).build())
                .build();
    }

    public static InlineKeyboardMarkup serviceCallInteractionMenuLess(long objectUniqueId, String language) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder.addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.serviceCall.showLess", language))
                        .callbackData(SHOW_INFO.getCommand() + "_" + "less" + "_" + objectUniqueId).build())
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.serviceCall.sendInteraction.toResponsible", language))
                        .callbackData(NEW_INTERACTION.getCommand() + "_"
                                + FROM_CUSTOMER.getValue() + "_" // FROM_CUSTOMER - to responsible
                                + objectUniqueId).build())
                .build();
    }

    public static InlineKeyboardMarkup menuOnOpen(long objectUniqueId, String lang, ButtonOptions options) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        builder.addRow();
        if (options.showInfoLess) {
            builder.addButton(InlineKeyboardButton.builder()
                    .text(L10n.getString("commands.serviceCall.showLess", lang))
                    .callbackData(SHOW_INFO.getCommand() + "_" + "less" + "_" + objectUniqueId)
                    .build());
        } else {
            builder.addButton(InlineKeyboardButton.builder()
                    .text(L10n.getString("commands.serviceCall.showMore", lang))
                    .callbackData(SHOW_INFO.getCommand() + "_" + "more" + "_" + objectUniqueId)
                    .build());
        }
        if (options.showInteraction) {
            builder.addButton(InlineKeyboardButton.builder()
                    .text(L10n.getString("commands.serviceCall.sendInteraction.toResponsible", lang))
                    .callbackData(NEW_INTERACTION.getCommand() + "_"
                            + FROM_CUSTOMER.getValue() + "_" // FROM_CUSTOMER - to responsible
                            + objectUniqueId)
                    .build());
        }
        if (options.showFeedback) {
            builder.addRow()
                    .addButton(InlineKeyboardButton.builder()
                            .text(L10n.getString("commands.serviceCall.feedback.button.yes", lang))
                            .callbackData(FEEDBACK.getCommand() + "_" + SOLUTION_ACCEPTED.getCommand() + "_" + objectUniqueId)
                            .build())
                    .addButton(InlineKeyboardButton.builder()
                            .text(L10n.getString("commands.serviceCall.feedback.button.no", lang))
                            .callbackData(FEEDBACK.getCommand() + "_" + SOLUTION_REJECTED.getCommand() + "_" + objectUniqueId)
                            .build());
        }
        return builder.build();
    }

    public static InlineKeyboardMarkup serviceCallsMenu(String language) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder
                .addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("menu.main.button.requests.caller.text", language))
                        .callbackData(SHOW_SC_CALLER.getCommand()).build())
                .addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("menu.main.button.back", language))
                        .callbackData(MENU_BACK.getCommand()).build())
                .build();
    }

    public static class ButtonOptions {
        private boolean showInfoLess;
        private boolean showInteraction;
        private boolean showFeedback;

        public ButtonOptions showInfoLess(boolean showInfoLess) {
            this.showInfoLess = showInfoLess;
            return this;
        }

        public ButtonOptions showInteraction(boolean showInteraction) {
            this.showInteraction = showInteraction;
            return this;
        }

        public ButtonOptions showFeedback(boolean showFeedback) {
            this.showFeedback = showFeedback;
            return this;
        }
    }
}
