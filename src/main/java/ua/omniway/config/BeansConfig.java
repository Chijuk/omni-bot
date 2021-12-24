package ua.omniway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.telegram.telegrambots.facilities.filedownloader.TelegramFileDownloader;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAsync
@EnableRetry
@PropertySource("file:${catalina.base}/conf/bot/omni-bot/bot-properties.xml")
public class BeansConfig {

    @Bean(name = "asyncOtExecutor")
    public Executor asyncOtExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncOtThread-");
        executor.initialize();
        return executor;
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(1000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }

    @Bean
    public TelegramFileDownloader telegramFileDownloader(@Value("${bot.token}") String token) {
        return new TelegramFileDownloader(() -> token);
    }
}
