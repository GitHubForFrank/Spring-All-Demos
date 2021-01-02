package com.zmz.springcloud.gateway.server.api.controller;

import com.zmz.springcloud.gateway.server.application.service.impl.DynamicRouteServiceImpl;
import com.zmz.springcloud.gateway.server.api.dto.GatewayFilterDefinitionDto;
import com.zmz.springcloud.gateway.server.api.dto.GatewayPredicateDefinitionDto;
import com.zmz.springcloud.gateway.server.api.dto.GatewayRouteDefinitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 11260
 * @create 2021-01-02 18:20
 */
@RequestMapping("/gateway/server")
@RestController
public class GatewayServerController {

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    /**
     * 增加路由
     * @param gatewayRouteDefinition xxx
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestBody GatewayRouteDefinitionDto gatewayRouteDefinition) {
        String flag = "fail";
        try {
            RouteDefinition definition = assembleRouteDefinition(gatewayRouteDefinition);
            flag = this.dynamicRouteService.add(definition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除路由
     * @param id
     * @return
     */
    @DeleteMapping("/routes/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable String id) {
        try {
            return this.dynamicRouteService.delete(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新路由
     * @param gatewayRouteDefinition
     * @return
     */
    @PostMapping("/update")
    public String update(@RequestBody GatewayRouteDefinitionDto gatewayRouteDefinition) {
        RouteDefinition definition = assembleRouteDefinition(gatewayRouteDefinition);
        return this.dynamicRouteService.update(definition);
    }

    /**
     * 把前端传递的参数转换成路由对象
     * @param gatewayRouteDefinition
     * @return
     */
    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinitionDto gatewayRouteDefinition) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gatewayRouteDefinition.getId());
        definition.setOrder(gatewayRouteDefinition.getOrder());

        //设置断言
        List<PredicateDefinition> pdList=new ArrayList<>();
        List<GatewayPredicateDefinitionDto> gatewayPredicateDefinitionList=gatewayRouteDefinition.getPredicates();
        for (GatewayPredicateDefinitionDto gpDefinition: gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        //设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinitionDto> gatewayFilters = gatewayRouteDefinition.getFilters();
        for(GatewayFilterDefinitionDto filterDefinition : gatewayFilters){
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);

        URI uri = null;
        if(gatewayRouteDefinition.getUri().startsWith("http")){
            uri = UriComponentsBuilder.fromHttpUrl(gatewayRouteDefinition.getUri()).build().toUri();
        }else{
            uri = URI.create(gatewayRouteDefinition.getUri());
        }
        definition.setUri(uri);
        return definition;
    }

    @RequestMapping("/default/fallback")
    public Map<String,String> defaultFallBack(){
        System.out.println("降级操作...");
        Map<String,String> map = new HashMap<>(3);
        map.put("resultCode","fail");
        map.put("resultMessage","服务异常");
        map.put("resultObj","null");
        return map;
    }

}
