package ua.omniway.bot.telegram.bothandlerapi.handlers.register;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ua.omniway.bot.telegram.bothandlerapi.bots.AbstractTelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DefaultDbContext;
import ua.omniway.bot.telegram.bothandlerapi.handlers.DocumentMessageHandler;
import ua.omniway.bot.telegram.bothandlerapi.handlers.MessageHandler;
import ua.omniway.models.db.ActiveAction;
import ua.omniway.models.db.DbUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultHandlerRegisterTest {
    private DefaultHandlerRegister full;
    private DefaultHandlerRegister empty;

    @BeforeEach
    void setUp() {
        full = new DefaultHandlerRegister();
        full.addHandler(new MessageHandler() {
            @Override
            public boolean onMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
                return false;
            }

            @Override
            public int getRequiredAccessLevel() {
                return 0;
            }
        });
        full.addHandler(new MessageHandler() {
            @Override
            public boolean onMessage(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
                return false;
            }

            @Override
            public int getRequiredAccessLevel() {
                return 5;
            }
        });
        full.addHandler(new DocumentMessageHandler() {
            @Override
            public boolean onDocumentSent(AbstractTelegramBot bot, Update update, Message message, DefaultDbContext context) throws Exception {
                return false;
            }

            @Override
            public int getRequiredAccessLevel() {
                return 3;
            }
        });
        empty = new DefaultHandlerRegister();
    }

    @AfterEach
    void tearDown() {
        full = null;
        empty = null;
    }

    @Test
    void getAvailableHandlersForUser() {
        final DbUser dbUserStart = new DbUser();
        dbUserStart.setActiveAction(ActiveAction.START);
        final DbUser dbUserMenu = new DbUser();
        dbUserMenu.setActiveAction(ActiveAction.MAIN_MENU);
        DefaultDbContext context = DefaultDbContext.builder().user(new User())
                .dbUser(dbUserStart).build();

        assertEquals(1, full.findByClass(MessageHandler.class, context).size(), "Expect 1 item");
        assertTrue(full.findByClass(DocumentMessageHandler.class, context).isEmpty(), "Expect empty list");
        assertTrue(empty.findByClass(MessageHandler.class, context).isEmpty(), "Expect empty list");
        context.setDbUser(dbUserMenu);
        assertEquals(1, full.findByClass(MessageHandler.class, context).size(), "Expect 1 item");
    }
}