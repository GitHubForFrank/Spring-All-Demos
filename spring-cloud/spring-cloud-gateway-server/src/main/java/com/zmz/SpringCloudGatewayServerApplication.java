package com.zmz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Refer : https://blog.csdn.net/u010734213/article/details/107063954
 *
 * @author ASNPHDG
 * @create 2020-01-26 21:31
 */
@Slf4j
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringCloudGatewayServerApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication.run(SpringCloudGatewayServerApplication.class, args);
        log.info("Application.main.completed");
    }

}

