package com.zmz.app;

import com.zmz.core.config.datasource.DynamicDataSourceRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Refer : https://www.cnblogs.com/hsbt2333/p/9347249.html
 * @author : ASNPHDG
 * @create : 2020-01-13 11:06 AM
 */
@Slf4j
@ComponentScan(basePackages = {"com"})
@SpringBootApplication
@EnableTransactionManagement

public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        log.debug("Application.configure");
        return builder.sources(Application.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.debug("Application.onStartup.begin");
        servletContext.setInitParameter("p-name", "p-value");
        super.onStartup(servletContext);
        log.debug("Application.onStartup.completed");
    }

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("Application.main.completed");
    }

}