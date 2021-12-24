package ua.omniway.bot.telegram.bothandlerapi.handlers;

public class ContextInitializationException extends Exception {
    public ContextInitializationException(Exception e) {
        super(e);
    }

    public ContextInitializationException(String message) {
        super(message);
    }
}

