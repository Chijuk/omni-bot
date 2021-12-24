package ua.omniway.bot.telegram.bothandlerapi.util;

import org.junit.jupiter.api.Test;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultCommand;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultCommandTest {
    final String text = "/name_param1_param2_param3";
    final String text2 = "/name param1 param2 param3";

    @Test
    void getArgs() {
        DefaultCommandImpl parser = new DefaultCommandImpl();
        assertEquals(new String[]{"param1", "param2", "param3"}.length, parser.getParseArgs(text).length, "Expect equal array length");
        assertEquals("param2", parser.getParseArgs(text)[1], "Expect equal strings");
        assertEquals(0, parser.getParseArgs("/justCommand").length, "Expect empty array");
        assertEquals(0, parser.getParseArgs("").length, "Expect empty array");
        assertEquals(0, parser.getParseArgs("/name_").length, "Expect empty array");
        assertThrows(NullPointerException.class, () -> parser.getParseArgs(null), "Expect NullPointerException");
        DefaultCommand parser2 = new DefaultCommandImpl2();
        assertEquals(new String[]{"param1", "param2", "param3"}.length, parser2.getParseArgs(text2).length, "Expect equal array length");
        assertEquals("param2", parser2.getParseArgs(text2)[1], "Expect equal strings");
        assertEquals(0, parser2.getParseArgs("/justCommand").length, "Expect empty array");
        assertEquals(0, parser2.getParseArgs("").length, "Expect empty array");
        assertEquals(0, parser2.getParseArgs("/name ").length, "Expect empty array");
        assertThrows(NullPointerException.class, () -> parser2.getParseArgs(null), "Expect NullPointerException");
    }

    static class DefaultCommandImpl implements DefaultCommand {

        @Override
        public String getCommand() {
            return null;
        }
    }

    static class DefaultCommandImpl2 implements DefaultCommand {

        @Override
        public String getCommand() {
            return null;
        }

        @Override
        public Pattern getArgsSplitPattern() {
            return Pattern.compile(" ");
        }
    }
}