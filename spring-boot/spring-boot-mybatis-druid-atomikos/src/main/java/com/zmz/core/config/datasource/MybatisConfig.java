package com.zmz.core.config.datasource;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zmz.core.config.datasource.annotation.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ASNPHDG
 * @create 2020-01-13 3:39 PM
 */
@Slf4j
@Configuration
@PropertySources({ @PropertySource(value = "classpath:mybatis.properties", ignoreResourceNotFound = true, encoding = "UTF-8") })
@AutoConfigureAfter({DruidConfig.class})
@EnableTransactionManagement
public class MybatisConfig {

    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    @Value("#{'${mybatis.mapperLocations}'.split(',')}")
    private List<String> mapperLocations;

    @Value("${mybatis.configLocation}")
    private String configLocation;

    @Autowired
    @Qualifier(value = "master")
    DataSource masterDataSource;

    @Autowired(required = false)
    @Qualifier(value = "slave")
    DataSource slaveDataSource;

    @Bean(name = "multipleDataSource")
    public DataSource multipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map< Object, Object > targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.MASTER.getValue(), masterDataSource);
        // 数据源上下文，用于管理数据源与记录已经注册的数据源key
        DataSourceContextHolder.dataSourceIds.add(DataSourceEnum.MASTER.getValue());
        if(slaveDataSource!=null){
            targetDataSources.put(DataSourceEnum.SLAVE.getValue(), slaveDataSource);
            DataSourceContextHolder.dataSourceIds.add(DataSourceEnum.SLAVE.getValue());
        }
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(masterDataSource);
        return multipleDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(multipleDataSource());

        // 阅读类型别名包装
        sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

        // 设置xxxxMapper.xml和Java XxxxMapper.java文件位置的所有映射器位置
        List<Resource> resourcesList = getAllMappersLocation();
        if ( null != resourcesList ) {
            sessionFactoryBean.setMapperLocations( resourcesList.toArray(new Resource[0]) );
        }

        // 设置mybatis-config.xml配置文件位置
        log.info("configLocation = " + configLocation);
        sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

        // 分页和SQL打印插件
        Interceptor[] plugins = new Interceptor[]{paginationInterceptor(), sqlPrintInterceptor()};
        sessionFactoryBean.setPlugins(plugins);

        // 全局配置
        DbConfig dbConfig = new DbConfig();
//        dbConfig.setIdType(IdType.AUTO);
//        dbConfig.setColumnLike(true);
//        dbConfig.setTablePrefix("t_");
//        dbConfig.setCapitalMode(false);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setRefresh(true);
        // 操作数据库（insert/update）的时候自动填充插件
        globalConfig.setMetaObjectHandler(new MetaObjectHandlerConfig());
        sessionFactoryBean.setGlobalConfig(globalConfig);
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager multipleTransactionManager(@Qualifier("multipleDataSource")DataSource multipleDataSource) {
        return new DataSourceTransactionManager(multipleDataSource);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public SqlPrintInterceptor sqlPrintInterceptor(){
        return new SqlPrintInterceptor();
    }

    public List<Resource> getAllMappersLocation(){
        log.info("mapperLocations: " + (null == mapperLocations ? "null" : ""+mapperLocations.size()));
        List<Resource> resourcesList = null;
        for ( String mapperLocation : mapperLocations) {
            log.info("mapperLocation:" + mapperLocation);
            try {
                Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
                if (null != resources && resources.length > 0) {
                    log.info("resources:" + resources.length);
                    if (null == resourcesList) {
                        resourcesList = new ArrayList<>();
                    }
                    resourcesList.addAll(Arrays.asList(resources));
                }
            } catch (Exception ex) {
                //Ignore wrong mapperLocation and no-resource location
                log.debug("WRONG mapperLocation:" + mapperLocation);
            }
        }
        return resourcesList;
    }

}
