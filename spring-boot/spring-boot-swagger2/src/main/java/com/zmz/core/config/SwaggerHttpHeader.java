package com.zmz.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-26 22:02
 */
@Getter
@Setter
@Component
@PropertySource(value = "classpath:swagger2.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@ConfigurationProperties(prefix="swagger2.ui.header")
public class SwaggerHttpHeader {
    private List<String> name = new ArrayList<>();
    private List<String> defaultValue = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> modelRef = new ArrayList<>();
    private List<Boolean> required = new ArrayList<>();
}

