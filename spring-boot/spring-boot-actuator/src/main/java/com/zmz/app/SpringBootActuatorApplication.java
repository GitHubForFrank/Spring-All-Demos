package com.zmz.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ASNPHDG
 * @create 2020-01-31 10:14
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringBootActuatorApplication {

	public static void main(String[] args) {
		log.debug("Application.main.begin");
		SpringApplication.run(SpringBootActuatorApplication.class,args);
		log.debug("Application.main.completed");
	}
}
