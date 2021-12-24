package ua.omniway.bot.telegram.keyboards;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import static org.junit.jupiter.api.Assertions.*;

class ReplyKeyboardBuilderTest {

    @Test
    @DisplayName("Test of builder outer representation")
    void testBuilder() {
        ReplyKeyboardBuilder builder = new ReplyKeyboardBuilder();
        ReplyKeyboardMarkup keyboard = builder
                .oneTimeKeyboard(false)
                .resizeKeyboard(true)
                .selective(false)
                .addRow()
                .addButton(new KeyboardButton("1.1"))
                .addButton(new KeyboardButton("1.2"))
                .addRow()
                .addButton(new KeyboardButton("2.1"))
                .addButton(new KeyboardButton("2.2"))
                .addButton(new KeyboardButton("2.3"))
                .build();
        assertEquals(2, keyboard.getKeyboard().size(), "Expect equal keyboard row size");
        assertEquals(3, keyboard.getKeyboard().get(1).size(), "Expect equal buttons count");
        assertFalse(keyboard.getOneTimeKeyboard(), "Expect oneTimeKeyboard is false");
        assertTrue(keyboard.getResizeKeyboard(), "Expect resizeKeyboard is true");
        assertFalse(keyboard.getSelective(), "Expect selective is false");
    }

    @Test
    @DisplayName("Test of builder inner structure")
    void innerTestOfBuilder() {
        ReplyKeyboardBuilder builder = new ReplyKeyboardBuilder();
        assertThrows(NullPointerException.class, () -> builder.addButton(null), "Expect NullPointerException");
        assertThrows(IllegalArgumentException.class, () -> builder.addButton(new KeyboardButton()), "Expect IllegalArgumentException");
        assertNotNull(builder.build(), "Expect not null object");
    }
}