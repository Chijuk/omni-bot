package ua.omniway;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.Banner;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.omniway.bot.telegram.DefaultBotAccessLevelValidator;
import ua.omniway.bot.telegram.TelegramBot;
import ua.omniway.bot.telegram.bothandlerapi.handlers.TelegramHandler;
import ua.omniway.services.app.CommonsOtApiService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

@Slf4j
@WebListener
public class ApplicationInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        log.info("--> Check OMNITRACKER SOAP connection...");
        CommonsOtApiService commonsApi = context.getBean(CommonsOtApiService.class);
        commonsApi.checkConnection();
        log.info("--> Starting Telegram bot...");
        TelegramBot bot = context.getBean(TelegramBot.class);
        bot.setAccessLevelValidator(new DefaultBotAccessLevelValidator());
        Map<String, TelegramHandler> handlers = context.getBeansOfType(TelegramHandler.class);
        handlers.values().forEach(bot::addHandler);
        bot.run();
        log.info("--> End application initialization");
        printGreeting(context);
    }

    private void printGreeting(ApplicationContext context) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Banner greeting = context.getBean(Banner.class);
            greeting.printBanner(context.getEnvironment(), ApplicationInitializer.class, new PrintStream(baos));
            log.info("\n" + baos);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LogManager.shutdown();
    }
}