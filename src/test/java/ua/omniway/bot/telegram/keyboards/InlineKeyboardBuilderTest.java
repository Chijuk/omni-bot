package ua.omniway.bot.telegram.keyboards;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import static org.junit.jupiter.api.Assertions.*;

class InlineKeyboardBuilderTest {

    @Test
    @DisplayName("Test of builder outer representation")
    void testBuilder() {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        InlineKeyboardMarkup keyboard = builder
                .addRow()
                .addButton(new InlineKeyboardButton("1.1"))
                .addButton(new InlineKeyboardButton("1.2"))
                .addRow()
                .addButton(new InlineKeyboardButton("2.1"))
                .addButton(new InlineKeyboardButton("2.2"))
                .addButton(new InlineKeyboardButton("2.3"))
                .build();
        assertEquals(2, keyboard.getKeyboard().size(), "Expect equal button row size");
        assertEquals(3, keyboard.getKeyboard().get(1).size(), "Expect equal buttons count");
    }

    @Test
    @DisplayName("Test of builder inner structure")
    void innerTestOfBuilder() {
        InlineKeyboardBuilder builder = new InlineKeyboardBuilder();
        assertThrows(NullPointerException.class, () -> builder.addButton(null), "Expect NullPointerException");
        assertThrows(IllegalArgumentException.class, () -> builder.addButton(new InlineKeyboardButton()), "Expect IllegalArgumentException");
        assertNotNull(builder.build(), "Expect not null object");
    }
}