package com.zmz.springcloud.gateway.server.config.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器
 * 所有请求都会执行
 * @author 11260
 * @create 2021-01-02 14:49
 */
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 执行逻辑
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().toString();
        System.out.println(" uri : " + uri);
        return chain.filter(exchange);
    }

    /**
     * 执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }

}
