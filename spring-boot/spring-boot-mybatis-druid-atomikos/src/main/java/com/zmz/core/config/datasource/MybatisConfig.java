package com.zmz.core.config.datasource;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.config.GlobalConfig.DbConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageInterceptor;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
        if(slaveDataSource!=null){
            targetDataSources.put(DataSourceEnum.SLAVE.getValue(), slaveDataSource);
        }
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(masterDataSource);
        return multipleDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() {
        log.info("MybatisConfig.sqlSessionFactory");
        try {
            return getMybatisPlusSqlSessionFactory();
        } catch (Exception e) {
            log.error("mybatis sqlSessionFactoryBean create error",e);
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        log.info("sqlSessionFactory(==null): " + (null == sqlSessionFactory));
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager multipleTransactionManager(@Qualifier("multipleDataSource")DataSource dataSource) {
        log.info("MybatisConfig.sqlSessionTemplate");
        return new DataSourceTransactionManager(dataSource);
    }

    /***
     * Get SqlSessionFactory for Mybatis-plus
     * **/
    private SqlSessionFactory getMybatisPlusSqlSessionFactory() {
        log.info("MybatisConfig.sqlSessionFactory");
        try {
            MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(multipleDataSource());

            //Read typeAliasesPackage
            log.info("typeAliasesPackage=" + typeAliasesPackage);
            sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

            //Set all mappers location of xxxxMapper.xml & Java XxxxMapper.java files location
            List<Resource> resourcesList = getAllMappersLocation();
            if ( null != resourcesList ) {
                log.info("resourcesList:" + resourcesList.size());
                sessionFactoryBean.setMapperLocations( resourcesList.toArray(new Resource[0]) );
            }

            //Set mybatis-config.xml configuration file location
            log.info("configLocation=" + configLocation);
            sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
						/*
            MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
            mybatisConfiguration.setMapUnderscoreToCamelCase(false);
            mybatisConfiguration.setCacheEnabled(true);
            sessionFactoryBean.setConfiguration(mybatisConfiguration);
						*/

            //Plugin for pagination & SQL print
            Interceptor[] plugins = new Interceptor[]{paginationInterceptor(), sqlPrintInterceptor()};
            sessionFactoryBean.setPlugins(plugins);

            DbConfig dbConfig = new DbConfig();
            //dbConfig.setIdType(IdType.AUTO);
            //dbConfig.setDbType(DbType.POSTGRE_SQL);
            //dbConfig.setColumnLike(true);
            //dbConfig.setTablePrefix("t_");
            //dbConfig.setCapitalMode(false);

            GlobalConfig globalConfig = new GlobalConfig();
            globalConfig.setDbConfig(dbConfig);
            globalConfig.setRefresh(true);

            /* 自动填充插件 */
            globalConfig.setMetaObjectHandler(new MetaObjectHandlerConfig());

            sessionFactoryBean.setGlobalConfig(globalConfig);

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            log.error("mybatis resolver mapper*xml is error",e);
            return null;
        } catch (Exception e) {
            log.error("mybatis sqlSessionFactoryBean create error",e);
            return null;
        }
    }

    /**
     * Plugin for Pagination query for MyBatis, not Mybatis-plus
     * 1. reasonable : true
     *      3.3.0版本可用 - 分页参数合理化，默认false禁用
     *      启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
     *      禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据
     *
     * 2. dialect : postgresql
     *      4.0.0以后版本可以不设置该参数
     *
     * 3. offsetAsPageNum : true
     *      该参数默认为false
     *      设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
     *      和startPage中的pageNum效果一样
     *
     * 4. rowBoundsWithCount : true
     *      该参数默认为false
     *      设置为true时，使用RowBounds分页会进行count查询
     *
     * 5. pageSizeZero : true
     *      该参数默认为false. 设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果
     *      （相当于没有执行分页查询，但是返回结果仍然是Page类型）
     *
     * 6. supportMethodsArguments : true
     *      3.5.0版本可用 - 为了支持startPage(Object params)方法
     *      增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值
     *      可以配置pageNum,pageSize,count,pageSizeZero,reasonable,orderBy,不配置映射的用默认值
     *      不理解该含义的前提下，不要随便复制该配置
     *      <property name="params" value="pageNum=start;pageSize=limit;"/>
     *      支持通过Mapper接口参数来传递分页参数
     *
     * 7. returnPageInfo : check
     *      always总是返回PageInfo类型,check检查返回类型是否为PageInfo,none返回Page
     *
     * */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties props = new Properties();
        props.setProperty("reasonable", "true");
        props.setProperty("supportMethodsArguments", "true");
        props.setProperty("returnPageInfo", "check");
        props.setProperty("params", "count=countSql");
        pageInterceptor.setProperties(props);
        return pageInterceptor;
    }

    /**
     * Mybatis-plus Pagination plugin<br>
     * Document: http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.info("MybatisConfig.paginationInterceptor");
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }

    @Bean
    public SqlPrintInterceptor sqlPrintInterceptor(){
        log.info("MybatisConfig.sqlPrintInterceptor");
        return new SqlPrintInterceptor();
    }

    /**
     * Performance analysis, not suggest for production
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor(){
        log.info("MybatisConfig.performanceInterceptor");
        return new PerformanceInterceptor();
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
