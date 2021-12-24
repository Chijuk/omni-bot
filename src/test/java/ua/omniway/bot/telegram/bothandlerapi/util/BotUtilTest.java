package ua.omniway.bot.telegram.bothandlerapi.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BotUtilTest {

    @Test
    void cleanBotNick() {
        assertEquals("/osc_576788", BotUtil.cleanBotNick("/osc_576788", "CEOmnitracker_bot"), "Expect /osc_576788");
        assertEquals("/osc_576788", BotUtil.cleanBotNick("@CEOmnitracker_bot osc_576788", "CEOmnitracker_bot"), "Expect /osc_576788");
        assertEquals("/osc_576788", BotUtil.cleanBotNick("/osc_576788@CEOmnitracker_bot", "CEOmnitracker_bot"), "Expect /osc_576788");
    }
}