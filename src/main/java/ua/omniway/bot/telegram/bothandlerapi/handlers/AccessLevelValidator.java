package ua.omniway.bot.telegram.bothandlerapi.handlers;

import org.telegram.telegrambots.meta.bots.AbsSender;

public interface AccessLevelValidator {
    /**
     * Bot level validator within user
     *
     * @param context handler context for user
     * @return {@code true} if context user has sufficient access level, {@code false} otherwise
     */
    boolean validate(DefaultDbContext context);

    /**
     * Fires if {@link AccessLevelValidator#validate} return {@code false}
     *
     * @param context handler context for user
     */
    default void onValidationError(DefaultDbContext context, AbsSender sender) {
    }
}
