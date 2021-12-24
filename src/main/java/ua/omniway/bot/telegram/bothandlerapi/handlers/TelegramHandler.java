package ua.omniway.bot.telegram.bothandlerapi.handlers;

public interface TelegramHandler {
    /**
     * Future releases
     *
     * @return The access level required to execute this command
     */
    default int getRequiredAccessLevel() {
        return 0;
    }
}
