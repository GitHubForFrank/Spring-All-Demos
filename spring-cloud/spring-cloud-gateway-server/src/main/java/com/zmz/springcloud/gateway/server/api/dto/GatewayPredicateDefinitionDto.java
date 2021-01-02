package com.zmz.springcloud.gateway.server.api.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言模型
 * @author 11260
 * @create 2021-01-02 14:49
 */
@Setter
@Getter
public class GatewayPredicateDefinitionDto {
    /**
     * 断言对应的Name
     */
    private String name;
    /**
     * 配置的断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
