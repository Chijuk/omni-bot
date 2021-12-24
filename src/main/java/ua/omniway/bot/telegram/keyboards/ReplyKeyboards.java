package ua.omniway.bot.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import ua.omniway.services.app.L10n;

public class ReplyKeyboards {

    private ReplyKeyboards() {
    }

    public static ReplyKeyboardMarkup mainMenu(String language) {
        ReplyKeyboardBuilder builder = new ReplyKeyboardBuilder();
        return builder.oneTimeKeyboard(false)
                .resizeKeyboard(true)
                .addRow()
                .addButton(KeyboardButton.builder().text(L10n.getString("bot.button.menu", language))
                        .build())
                .build();
    }
}
