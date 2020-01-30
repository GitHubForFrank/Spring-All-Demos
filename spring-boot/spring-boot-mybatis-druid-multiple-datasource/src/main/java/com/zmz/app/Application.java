package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : ASNPHDG
 * @create : 2020-01-13 11:06 AM
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("Application.main.completed");
    }

}