package com.zmz.core.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
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

    @Bean("dataSource")
    @Primary
    public DataSource masterDataSource() throws Exception{
        Properties properties = getDataSourceProperties("spring.datasource");
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

        // 如下不做非空判断,是连接数据库必须字段
        properties.put("driverClassName", env.getProperty(prefix+".driverClassName"));
        properties.put("url", env.getProperty(prefix+".url"));
        properties.put("username", env.getProperty(prefix+".username"));
        properties.put("password", env.getProperty(prefix+".password"));

        // 如下字段可以为空,是连接数据库非必须字段,列举了部分，可以自由控制
        String druidPrefix = prefix+".druid";
        setProperties(properties,"initialSize",druidPrefix+".minIdle");
        setProperties(properties,"minIdle",druidPrefix+".initialSize");
        setProperties(properties,"maxActive",druidPrefix+".maxActive");
        setProperties(properties,"maxWait",druidPrefix+".maxWait");
        setProperties(properties,"timeBetweenEvictionRunsMillis",druidPrefix+".timeBetweenEvictionRunsMillis");
        setProperties(properties,"minEvictableIdleTimeMillis",druidPrefix+".minEvictableIdleTimeMillis");
        setProperties(properties,"validationQuery",druidPrefix+".validationQuery");
        setProperties(properties,"filters",druidPrefix+".druid.sys.filters");
        setProperties(properties,"validationQueryTimeout",druidPrefix+".validationQueryTimeout");
        setProperties(properties,"testWhileIdle",druidPrefix+".testWhileIdle");
        setProperties(properties,"testOnBorrow",druidPrefix+".testOnBorrow");
        setProperties(properties,"testOnReturn",druidPrefix+".testOnReturn");

        System.out.println("==========================================");
        System.out.println(properties);
        System.out.println("==========================================");
        return properties;
    }

    private void setProperties(Properties properties,String key,String envKey){
        String value = env.getProperty(envKey);
        if(value!=null){
            properties.put(key, value);
        }
    }

}
