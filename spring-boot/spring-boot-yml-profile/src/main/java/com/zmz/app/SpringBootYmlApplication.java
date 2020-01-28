package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ASNPHDG
 * @create 2020-01-28 17:04
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringBootYmlApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication app = new SpringApplication(SpringBootYmlApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("Application.main.completed");
    }

}

