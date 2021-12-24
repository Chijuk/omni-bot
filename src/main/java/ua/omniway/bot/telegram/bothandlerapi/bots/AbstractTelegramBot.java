package ua.omniway.bot.telegram.bothandlerapi.bots;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ua.omniway.bot.telegram.bothandlerapi.handlers.*;
import ua.omniway.bot.telegram.bothandlerapi.handlers.functional.ThrowableConsumer;
import ua.omniway.bot.telegram.bothandlerapi.handlers.functional.ThrowableFunction;
import ua.omniway.bot.telegram.bothandlerapi.handlers.register.DefaultHandlerRegister;
import ua.omniway.bot.telegram.bothandlerapi.handlers.register.HandlerRegister;
import ua.omniway.bot.telegram.bothandlerapi.util.BotUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public abstract class AbstractTelegramBot extends TelegramLongPollingBot {

    private final HandlerRegister handlerRegister;
    private final HandlerContext handlerContext;
    private AccessLevelValidator accessLevelValidator;

    private final String token;
    private final String username;

    protected AbstractTelegramBot(String token, String username, HandlerContext handlerContext) {
        this.token = token;
        this.username = username;
        this.handlerContext = handlerContext;
        this.handlerRegister = new DefaultHandlerRegister();
    }

    protected AbstractTelegramBot(String token, String username, HandlerRegister handlerRegister, HandlerContext handlerContext) {
        this.token = token;
        this.username = username;
        this.handlerRegister = handlerRegister;
        this.handlerContext = handlerContext;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.trace("Incoming {}", update.toString());
        try {
            if (update.hasChosenInlineQuery()) {
                // Handle Chosen inline query
                final DefaultDbContext context = handlerContext.init(update, update.getChosenInlineQuery().getFrom());
                handleUpdate(ChosenInlineQueryHandler.class,
                        handler -> handler.onChosenInlineQuery(this, update, update.getChosenInlineQuery(), context),
                        context
                );
            } else if (update.hasInlineQuery()) {
                // Handle inline query
                final DefaultDbContext context = handlerContext.init(update, update.getInlineQuery().getFrom());
                handleUpdate(InlineQueryHandler.class,
                        handler -> handler.onInlineQuery(this, update, update.getInlineQuery(), context),
                        context
                );
            } else if (update.hasCallbackQuery()) {
                // Handle callback query
                final DefaultDbContext context = handlerContext.init(update, update.getCallbackQuery().getFrom());
                final String data = update.getCallbackQuery().getData();
                handleCommandUpdate(CallbackQueryHandler.class,
                        handler -> handler.executeCallbackEvent(this, update, data, context),
                        context,
                        data);
            } else if (update.hasEditedMessage()) {
                // Handle edited message
                final DefaultDbContext context = handlerContext.init(update, update.getEditedMessage().getFrom());
                handleUpdate(EditedMessageHandler.class,
                        handler -> handler.onEditMessage(this, update, update.getEditedMessage(), context),
                        context
                );
            } else if (update.hasMessage()) {
                // Handle message with attachment
                final DefaultDbContext context = handlerContext.init(update, update.getMessage().getFrom());
                if (hasAnyFileType(update.getMessage())) {
                    handleUpdate(FileMessageHandler.class,
                            handler -> handler.onFileSent(this, update, context),
                            context
                    );
                }
                // Handle command message
                if (update.getMessage().isCommand()) {
                    // Handle command message
                    final String message = BotUtil.cleanBotNick(update.getMessage().getText(), getBotUsername());
                    handleCommandUpdate(CommandHandler.class,
                            handler -> handler.executeCommandEvent(this, update, message, context),
                            context,
                            message);
                } else if (update.getMessage().hasText()) {
                    // Handle simple message
                    handleUpdate(MessageHandler.class,
                            handler -> handler.onMessage(this, update, update.getMessage(), context),
                            context
                    );
                }
            } else {
                log.warn("Update doesn't contains any handler. Update: {}", update);
            }
        } catch (Exception e) {
            log.error("Failed to handle incoming update", e);
        }
    }

    /**
     * @param clazz   handler class
     * @param action  action to execute
     * @param context update handler context
     * @param <T>     handler type
     */
    private <T extends TelegramHandler> void handleUpdate(Class<T> clazz, ThrowableFunction<T, Boolean> action, DefaultDbContext context) {
        if (!validateAccessLevel(context)) {
            accessLevelValidator.onValidationError(context, this);
            return;
        }
        final List<T> availableHandlers = handlerRegister.findByClass(clazz, context);
        if (availableHandlers.isEmpty()) {
            log.info("Found no available handler for class: {}, user: {}",
                    clazz.getSimpleName(), context.getUser().getUserName());
            return;
        }
        for (T handler : availableHandlers) {
            try {
                if (Boolean.TRUE.equals(action.apply(handler))) { // looks for only one handler that will consume action
                    break;
                }
            } catch (TelegramApiRequestException e) {
                log.error("API Exception caught on handler: {} response: {}",
                        handler.getClass().getSimpleName(), e.getApiResponse(), e);
            } catch (Exception e) {
                log.error("Exception caught on handler: {}", handler.getClass().getSimpleName(), e);
            }
        }
    }

    /**
     * @param clazz       handler class
     * @param action      action to execute
     * @param context     update handler context
     * @param userMessage message from user
     * @param <T>         handler type
     */
    private <T extends DefaultCommand> void handleCommandUpdate(Class<T> clazz, ThrowableConsumer<T> action, DefaultDbContext context, String userMessage) {
        if (!validateAccessLevel(context)) {
            accessLevelValidator.onValidationError(context, this);
            return;
        }
        final String command = parseCommand(userMessage);
        final T commandHandler = handlerRegister.findByName(clazz, command, context);
        if (commandHandler != null) {
            try {
                action.accept(commandHandler);
            } catch (TelegramApiRequestException e) {
                log.error("API Exception caught on handler: {}, message: {}, response: {} ",
                        commandHandler.getClass().getSimpleName(), userMessage, e.getApiResponse(), e);
            } catch (Exception e) {
                log.error("Exception caught on handler: {}, message: {}",
                        commandHandler.getClass().getSimpleName(), userMessage, e);
            }
        } else {
            log.warn("Found no available CommandHandler for message: {}, user: {}",
                    userMessage, context.getUser().getUserName());
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public HandlerContext getHandlerContext() {
        return handlerContext;
    }

    /**
     * @return pattern to parse command name
     */
    protected Pattern getCommandPattern() {
        return Pattern.compile("^(\\/[^_]*)");
    }

    /**
     * @param text text containing command
     * @return command name. Uses {@link AbstractTelegramBot#getCommandPattern()}
     */
    private String parseCommand(@NonNull String text) {
        final Matcher matcher = getCommandPattern().matcher(text);
        return matcher.find() ? matcher.group() : "";
    }

    /**
     * Sets the Access Level Validator instance that will be used for future access level validations
     *
     * @param accessLevelValidator the access level validator implementation
     */
    public void setAccessLevelValidator(AccessLevelValidator accessLevelValidator) {
        this.accessLevelValidator = accessLevelValidator;
    }

    /**
     * @return the Access Level Validator instance that will be used for future access level validations
     */
    public AccessLevelValidator getAccessLevelValidator() {
        return accessLevelValidator;
    }

    /**
     * Registers TelegramHandler instance into a collection of handlers
     *
     * @param handler CommandHandler instance
     */
    public void addHandler(TelegramHandler handler) {
        handlerRegister.addHandler(handler);
    }

    /**
     * Validates access level for bot within user
     *
     * @param context handler context for user
     * @return {@code true} if user is able to use this bot, {@code false} otherwise
     */
    private boolean validateAccessLevel(DefaultDbContext context) {
        return this.getAccessLevelValidator() == null || this.accessLevelValidator.validate(context);
    }

    /**
     * @param message Telegram message
     * @return true if update has any file type
     */
    private boolean hasAnyFileType(Message message) {
        return message.hasDocument() || message.hasPhoto() || message.hasVideo() || message.hasAudio()
                || message.hasVoice() || message.hasVideoNote();
    }
}