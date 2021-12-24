package ua.omniway.bot.telegram.bothandlerapi.handlers;

import lombok.NonNull;

import java.util.Arrays;
import java.util.regex.Pattern;

public interface DefaultCommand extends TelegramHandler {
    /**
     * @return The command that will trigger execution action method
     */
    String getCommand();

    /**
     * Future releases
     *
     * @return The category mapping whenever u type in /help that would group current command to the returned category
     */
    default String getCategory() {
        return getRequiredAccessLevel() > 0 ? "Protected [" + getRequiredAccessLevel() + " level] commands" : "Public commands";
    }

    /**
     * @return pattern to parse array of command args from command string
     */
    default Pattern getArgsSplitPattern() {
        return Pattern.compile("_");
    }

    /**
     * Uses {@link DefaultCommand#getArgsSplitPattern()}
     *
     * @param text command string containing command args
     * @return command args array
     */
    default String[] getParseArgs(@NonNull String text) {
        if (text.isEmpty()) return new String[0];
        final String[] argsArray = getArgsSplitPattern().split(text);
        return argsArray.length == 1 ? new String[0] : Arrays.copyOfRange(argsArray, 1, argsArray.length);
    }
}
