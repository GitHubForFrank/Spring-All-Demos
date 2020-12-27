package com.zmz.core.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该过滤器只会过滤被代理的url，当前应用中声明的接口不会被代理。
 * 所以，代理可以和权限部分一起存在。
 */
@Slf4j
@Component
public class ZuulProxyAuthFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest req = ctx.getRequest();
        log.info("访问URL：{}", req.getRequestURI());
        Object user = req.getSession().getAttribute("user");
//        if (user == null) {
//            throw new Exception("未登录");
//        }
        final HttpServletResponse resp = ctx.getResponse();
//        resp.setHeader("username", user.toString());
        return null;
    }
}
