package com.zmz.springcloud.gateway.server.api.controller;

import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter;
import org.springframework.cloud.gateway.filter.headers.XForwardedHeadersFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 11260
 * @create 2021-01-02 18:20
 */
@RestController
public class GatewayServerController {

    @RequestMapping("/defaultfallback")
    public Map<String,String> defaultFallBack(){
        System.out.println("降级操作...");
        Map<String,String> map = new HashMap<>(3);
        map.put("resultCode","fail");
        map.put("resultMessage","服务异常");
        map.put("resultObj","null");
        return map;
    }

}
