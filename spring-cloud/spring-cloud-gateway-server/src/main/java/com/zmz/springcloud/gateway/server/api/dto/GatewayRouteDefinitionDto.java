package com.zmz.springcloud.gateway.server.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 路由模型
 * @author 11260
 * @create 2021-01-02 14:49
 */
@Setter
@Getter
public class GatewayRouteDefinitionDto {
    /**
     * 路由的Id
     */
    private String id;
    /**
     * 路由断言集合配置
     */
    private List<GatewayPredicateDefinitionDto> predicates = new ArrayList<>();
    /**
     * 路由过滤器集合配置
     */
    private List<GatewayFilterDefinitionDto> filters = new ArrayList<>();
    /**
     * 路由规则转发的目标uri
     */
    private String uri;
    /**
     * 路由执行的顺序
     */
    private int order = 0;
}
