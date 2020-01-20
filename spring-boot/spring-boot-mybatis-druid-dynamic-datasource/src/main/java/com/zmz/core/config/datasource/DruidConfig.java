package com.zmz.core.config.datasource;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ASNPHDG
 * @create 2020-01-13 3:59 PM
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean servletRegistration() {
        //Add initialization parameter:initParams
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new StatViewServlet());
        servletRegistration.addUrlMappings("/druid/*");
        //White list
        servletRegistration.addInitParameter("allow", "127.0.0.1");
        //IP black list(When exist in both white list and black list, deny takes precedence over allow. ) : If deny is satisfied, prompt:Sorry, you are not permitted to view this page.
        servletRegistration.addInitParameter("deny", "192.168.1.73");
        //Login to check the account password of the information..
        servletRegistration.addInitParameter("loginUsername", "admin");
        servletRegistration.addInitParameter("loginPassword", "123456");
        //To define whether it possible to reset data?
        servletRegistration.addInitParameter("resetEnable", "false");
        return servletRegistration;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //Add filter rule
        filterRegistrationBean.addUrlPatterns("/*");
        //Add format information that does not need to be ignored.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
