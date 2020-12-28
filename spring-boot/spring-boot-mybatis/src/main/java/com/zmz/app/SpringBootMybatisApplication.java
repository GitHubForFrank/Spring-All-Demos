package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ASNPHDG
 * @create 2020-01-29 17:57
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringBootMybatisApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication.run(SpringBootMybatisApplication.class, args);
        log.info("Application.main.completed");
    }

}

