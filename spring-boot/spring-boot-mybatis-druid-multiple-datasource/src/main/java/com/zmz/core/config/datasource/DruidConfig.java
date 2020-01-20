package com.zmz.core.config.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zmz.core.config.datasource.condition.MultipleDataSourceCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author ASNPHDG
 * @create 2020-01-13 3:59 PM
 */
@Configuration
public class DruidConfig {

    @Autowired
    private Environment env;

    @Bean("master")
    @Primary
    public DataSource masterDataSource() throws Exception{
        Properties properties = getDataSourceProperties("spring.datasource");
        return DruidDataSourceFactory.createDataSource(properties);
    }

    @Bean("slave")
    @Conditional(MultipleDataSourceCondition.class)
    public DataSource slaveDataSource() throws Exception {
        Properties properties = getDataSourceProperties("spring.datasource.druid.slave");
        return DruidDataSourceFactory.createDataSource(properties);
    }

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

    private Properties getDataSourceProperties(String prefix) {
        Properties properties = new Properties();
        setProperties(properties,"initialSize",prefix+".minIdle");
        setProperties(properties,"minIdle",prefix+".initialSize");
        setProperties(properties,"maxActive",prefix+".maxActive");
        setProperties(properties,"maxWait",prefix+".maxWait");
        setProperties(properties,"timeBetweenEvictionRunsMillis",prefix+".timeBetweenEvictionRunsMillis");
        setProperties(properties,"minEvictableIdleTimeMillis",prefix+".minEvictableIdleTimeMillis");
        setProperties(properties,"validationQuery",prefix+".validationQuery");
        setProperties(properties,"filters",prefix+".druid.sys.filters");
        setProperties(properties,"validationQueryTimeout",prefix+".validationQueryTimeout");
        setProperties(properties,"testWhileIdle",prefix+".testWhileIdle");
        setProperties(properties,"testOnBorrow",prefix+".testOnBorrow");
        setProperties(properties,"testOnReturn",prefix+".testOnReturn");

        //如下不做非空判断,是连接数据库必须字段
        properties.put("driverClassName", env.getProperty(prefix+".driverClassName"));
        properties.put("url", env.getProperty(prefix+".url"));
        properties.put("username", env.getProperty(prefix+".username"));
        properties.put("password", env.getProperty(prefix+".password"));
        System.out.println("=============================");
        System.out.println(properties);
        System.out.println("=============================");
        return properties;
    }

    private void setProperties(Properties properties,String key,String envKey){
        String value = env.getProperty(envKey);
        if(value!=null){
            properties.put(key, value);
        }
    }

}
