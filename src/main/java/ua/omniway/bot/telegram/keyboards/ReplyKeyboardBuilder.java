package ua.omniway.bot.telegram.keyboards;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardBuilder {

    private Boolean resizeKeyboard;
    private Boolean oneTimeKeyboard;
    private Boolean selective;
    private final List<KeyboardRow> keyboard;
    private KeyboardRow keyboardRow;

    public ReplyKeyboardBuilder() {
        this.keyboard = new ArrayList<>();
    }

    public ReplyKeyboardBuilder resizeKeyboard(Boolean value) {
        resizeKeyboard = value;
        return this;
    }

    public ReplyKeyboardBuilder oneTimeKeyboard(Boolean value) {
        oneTimeKeyboard = value;
        return this;
    }

    public ReplyKeyboardBuilder selective(Boolean value) {
        selective = value;
        return this;
    }

    public ReplyKeyboardBuilder addRow() {
        keyboardRow = new KeyboardRow();
        keyboard.add(keyboardRow);
        return this;
    }

    public ReplyKeyboardBuilder addButton(@NonNull KeyboardButton keyboardButton) {
        if (keyboardRow == null) throw new IllegalArgumentException("Keyboard buttons row can't be null");
        keyboardRow.add(keyboardButton);
        return this;
    }

    public ReplyKeyboardMarkup build() {
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .oneTimeKeyboard(oneTimeKeyboard)
                .resizeKeyboard(resizeKeyboard)
                .selective(selective)
                .build();
    }
}
