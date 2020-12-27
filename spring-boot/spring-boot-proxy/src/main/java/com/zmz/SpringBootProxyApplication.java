package com.zmz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Refer : https://github.com/mitre/HTTP-Proxy-Servlet
 *
 * http://localhost:10190/app/swagger-ui.html
 * http://localhost:10190/app/api
 *
 * 可是实现重定向，但是能完成的功能非常简单，复杂的反向代理操作，还需要参考别的
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

