package com.zmz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * http://localhost:10190/app/swagger-ui.html
 * http://localhost:10190/app/api
 * @author ASNPHDG
 * @create 2020-01-26 21:31
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringBootProxyApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication.run(SpringBootProxyApplication.class, args);
        log.info("Application.main.completed");
    }

}

