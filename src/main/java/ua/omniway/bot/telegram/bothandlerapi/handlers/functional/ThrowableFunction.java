package ua.omniway.bot.telegram.bothandlerapi.handlers.functional;

import java.util.Objects;

@FunctionalInterface
public interface ThrowableFunction<T, R> {
    R apply(T var1) throws Exception;

    default <V> ThrowableFunction<V, R> compose(ThrowableFunction<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (v) -> {
            return this.apply(before.apply(v));
        };
    }

    default <V> ThrowableFunction<T, V> andThen(ThrowableFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t) -> {
            return after.apply(this.apply(t));
        };
    }

    static <T> ThrowableFunction<T, T> identity() {
        return (t) -> {
            return t;
        };
    }
}
