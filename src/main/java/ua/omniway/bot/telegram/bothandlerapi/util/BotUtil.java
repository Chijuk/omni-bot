package ua.omniway.bot.telegram.bothandlerapi.util;

public class BotUtil {

    private BotUtil() {
    }

    /**
     * @param text message's text
     * @return Text without @BotNickname if specified
     */
    public static String cleanBotNick(String text, String botUserName) {
        // Parse commands that goes like: @BotNickname help to /help
        if (text.startsWith("@" + botUserName + " ")) {
            text = '/' + text.substring(("@" + botUserName + " ").length());
        }
        // Parse commands that goes like: /help@BotNickname to /help
        else if (text.contains("@" + botUserName)) {
            text = text.replaceAll("@" + botUserName, "");
            if (text.charAt(0) != '/') {
                text = '/' + text;
            }
        }
        return text;
    }
}
