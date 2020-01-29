package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:45
 */
@Slf4j
@ComponentScan(basePackages = { "com.zmz"})
@SpringBootApplication
public class SpringBootLogbackApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication.run(SpringBootLogbackApplication.class, args);
        log.debug("Application.main.completed");
    }

}

