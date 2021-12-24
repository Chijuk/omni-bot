package ua.omniway;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@Slf4j
@SpringBootApplication
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringBootInitializer extends SpringBootServletInitializer {
    private ServletContext servletContext;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        this.servletContext = servletContext;
        super.onStartup(servletContext);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        checkConfigPath(System.getProperty("catalina.base") + "/conf/bot" + servletContext.getContextPath() + "/bot-properties.xml");
        builder.bannerMode(Banner.Mode.CONSOLE);
        builder.sources(SpringBootInitializer.class);
        return super.configure(builder);
    }

    @SneakyThrows
    private void checkConfigPath(String path) {
        log.info("--> Start application initialization...");
        log.info("--> Check config files...");
        ClassPathResource wsdl = new ClassPathResource("/wsdl/ot.wsdl");
        ServletContextResource manifest = new ServletContextResource(servletContext, "/META-INF/MANIFEST.MF");
        FileSystemResource config = new FileSystemResource(path);
        if (!wsdl.exists()) {
            log.error("Can not find wsdl file on location: {}", wsdl.getURI());
            throw new InitializationException("Can not find wsdl file");
        }
        if (!manifest.exists()) {
            log.error("Can not find manifest file on location: {}", manifest.getPath());
            throw new InitializationException("Can not find manifest file");
        }
        if (!config.exists()) {
            log.error("Can not find config file on location: {}", config.getPath());
            throw new InitializationException("Can not find config file");
        }
    }
}

