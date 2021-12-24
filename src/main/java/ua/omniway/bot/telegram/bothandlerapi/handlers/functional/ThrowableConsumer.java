package ua.omniway.bot.telegram.bothandlerapi.handlers.functional;

@FunctionalInterface
public interface ThrowableConsumer<T> {
    void accept(T var1) throws Exception;
}
