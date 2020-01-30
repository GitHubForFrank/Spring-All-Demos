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
        // 添加初始化参数
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new StatViewServlet());
        servletRegistration.addUrlMappings("/druid/*");
        // IP 白名单
        servletRegistration.addInitParameter("allow", "127.0.0.1");
        // IP黑名单（当白名单和黑名单中都存在时，拒绝优先于允许。）：如果满足拒绝，则提示：对不起，您不允许查看此页面。
        servletRegistration.addInitParameter("deny", "192.168.1.73");
        // 登录可以查看信息的帐户密码。
        servletRegistration.addInitParameter("loginUsername", "admin");
        servletRegistration.addInitParameter("loginPassword", "123456");
        // 定义是否可以重置数据
        servletRegistration.addInitParameter("resetEnable", "false");
        return servletRegistration;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息。
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
