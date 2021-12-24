package ua.omniway.bot.telegram.keyboards;

import lombok.NonNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardBuilder {
    private final List<List<InlineKeyboardButton>> keyboard;
    private List<InlineKeyboardButton> buttonsRow;

    public InlineKeyboardBuilder() {
        this.keyboard = new ArrayList<>();
    }

    public InlineKeyboardBuilder addRow() {
        buttonsRow = new ArrayList<>();
        keyboard.add(buttonsRow);
        return this;
    }

    public InlineKeyboardBuilder addButton(@NonNull InlineKeyboardButton button) {
        if (buttonsRow == null) throw new IllegalArgumentException("Keyboard buttons row can't be null");
        buttonsRow.add(button);
        return this;
    }

    public InlineKeyboardMarkup build() {
        return new InlineKeyboardMarkup(keyboard);
    }
}
