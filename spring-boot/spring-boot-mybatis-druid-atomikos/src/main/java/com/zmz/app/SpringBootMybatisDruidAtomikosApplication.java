package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ASNPHDG
 * @create 2020-01-30 18:47
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootMybatisDruidAtomikosApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication.run(SpringBootMybatisDruidAtomikosApplication.class,args);
        log.debug("Application.main.completed");
    }

}

