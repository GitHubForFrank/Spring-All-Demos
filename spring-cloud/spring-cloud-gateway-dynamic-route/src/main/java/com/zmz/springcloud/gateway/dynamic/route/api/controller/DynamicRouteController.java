package com.zmz.springcloud.gateway.dynamic.route.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 查询网关的路由信息
 * @author 11260
 * @create 2021-01-02 14:49
 */
@RestController
@RequestMapping("/route")
public class DynamicRouteController {

    @Autowired private RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 获取网关所有的路由信息
     * @return
     */
    @RequestMapping("/routes")
    public Flux<RouteDefinition> getRouteDefinitions(){
        return routeDefinitionLocator.getRouteDefinitions();
    }

}
