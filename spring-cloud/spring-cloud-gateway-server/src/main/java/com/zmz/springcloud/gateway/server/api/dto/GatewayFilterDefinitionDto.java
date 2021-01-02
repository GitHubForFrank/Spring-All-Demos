package com.zmz.springcloud.gateway.server.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器模型
 * @author 11260
 * @create 2021-01-02 14:49
 */
@Setter
@Getter
public class GatewayFilterDefinitionDto {
    /**
     * Filter Name
     */
    private String name;
    /**
     * 对应的路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
