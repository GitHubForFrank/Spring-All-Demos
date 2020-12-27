package com.zmz.core.config;

import com.google.common.collect.ImmutableMap;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.Servlet;
import java.util.Map;

/**
 * http://localhost:10190/app/swagger-ui.html
 * http://localhost:10190/api
 * @Description: 实现代理配置
 * @author ASNPHDG
 * @create 2020-01-26 22:17
 */
@Configuration
public class SolrProxyServletConfiguration {

    /**
     * 读取配置文件中路由设置
     */
    @Value("${proxy.servlet.url}")
    private String servletUrl;

    /**
     * 读取配置中代理目标地址
     */
    @Value("${proxt.target.url}")
    private String targetUrl;

    @Bean
    public Servlet createProxyServlet(){
        return new ProxyServlet();
    }

    @Bean
    public ServletRegistrationBean proxyServletRegistration2(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(createProxyServlet(), servletUrl);
        //设置网址以及参数
        Map<String, String> params = ImmutableMap.of(
                "targetUri", targetUrl,
                "log", "true");
        registrationBean.setInitParameters(params);
        return registrationBean;
    }

}