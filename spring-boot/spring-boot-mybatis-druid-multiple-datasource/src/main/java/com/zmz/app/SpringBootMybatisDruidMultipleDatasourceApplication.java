package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author : ASNPHDG
 * @create : 2020-01-13 11:06 AM
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringBootMybatisDruidMultipleDatasourceApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication.run(SpringBootMybatisDruidMultipleDatasourceApplication.class,args);
        log.info("Application.main.completed");
    }

}