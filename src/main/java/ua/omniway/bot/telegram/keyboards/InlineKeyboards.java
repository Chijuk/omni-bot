package ua.omniway.bot.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.omniway.models.rest.ApprovalVoteNotification;
import ua.omniway.services.app.L10n;
import ua.omniway.services.app.ServiceCallsCacheService;

import static ua.omniway.bot.telegram.handlers.menu.servicecalls.ServiceCallCallbackEnums.NEW_COMMAND;
import static ua.omniway.models.ot.ApprovalResultEnum.APPROVED;
import static ua.omniway.models.ot.ApprovalResultEnum.REJECTED;

public class InlineKeyboards {

    private InlineKeyboards() {
    }

    public static InlineKeyboardMarkup welcomeOptionsList(String language) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder.addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("bot.welcome.button.authorize", language))
                        .callbackData("/authorize_password").build())
                .build();
    }

    public static InlineKeyboardMarkup languageList() {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        builder.addRow();
        L10n.getSupportedLanguages().forEach(language ->
                builder.addButton(InlineKeyboardButton.builder()
                        .text(language.getEmoji() + " " + language.getName())
                        .callbackData("/l10n_" + language.getCode()).build()));
        return builder.build();
    }

    public static InlineKeyboardMarkup mainMenu(String language) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder
                .addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("menu.main.button.newRequest.text", language))
                        .callbackData(NEW_COMMAND.getCommand()).build())
                .addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("menu.main.button.requests.text", language))
                        .callbackData("/serviceCallsMenu").build())
                .build();
    }

    public static InlineKeyboardMarkup approvalMenu(ApprovalVoteNotification request, String language) {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        return builder.addRow()
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.approvalVote.approval.yes", language))
                        .callbackData("/approvalVote" + "_" + APPROVED.getAlias() + "_" + request.getObjectUniqueId()).build())
                .addButton(InlineKeyboardButton.builder()
                        .text(L10n.getString("commands.approvalVote.approval.no", language))
                        .callbackData("/approvalVote" + "_" + REJECTED.getAlias() + "_" + request.getObjectUniqueId()).build())
                .build();
    }

    /**
     * /openPage_[page]_[size]
     *
     * @param page current page
     * @param size total number of objects in list
     * @return InlineKeyboardMarkup with buttons represent pages of objects in list
     */
    public static InlineKeyboardMarkup pagesMenu(int page, int size, String identifier) {
        final String command = "/openPage" + identifier;
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        builder.addRow();
        if (size <= ServiceCallsCacheService.SC_ON_PAGE) {
            return builder.build();
        }
        InlineKeyboardButton current = new InlineKeyboardButton();
        current.setText("\u00B7 " + page + " \u00B7");
        current.setCallbackData(command + "_" + page + "_" + size);

        InlineKeyboardButton prev = null;
        InlineKeyboardButton next = null;

        int pagesCount = (int) Math.ceil((double) size / ServiceCallsCacheService.SC_ON_PAGE);

        if (page - 1 >= 1) {
            prev = new InlineKeyboardButton();
            prev.setText("< " + (page - 1));
            prev.setCallbackData(command + "_" + (page - 1) + "_" + size);
        }

        if (page + 1 <= pagesCount) {
            next = new InlineKeyboardButton();
            next.setText((page + 1) + " >");
            next.setCallbackData(command + "_" + (page + 1) + "_" + size);
        }
        // Collect page button menu
        if (prev != null) builder.addButton(prev);
        builder.addButton(current);
        if (next != null) builder.addButton(next);

        return builder.build();
    }
}
