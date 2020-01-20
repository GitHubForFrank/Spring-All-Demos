package com.zmz.core.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ASNPHDG
 * @create 2020-01-14 5:14 PM
 */
@Slf4j
@Configuration
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * 配置上下文（也可以理解为配置文件的获取工具）
     */
    private Environment evn;
    /**
     * 参数绑定工具 springboot2.0新推出
     */
    private Binder binder;

    /**
     * 别名
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }


    /**
     * ImportBeanDefinitionRegistrar接口的实现方法，通过该方法可以按照自己的方式注册bean
     *
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        try {
            // 获取所有数据源配置
            Map config, defaultDataSourceProperties;
            //存储我们注册的数据源
            Map<String, DataSource> customDataSources = new HashMap<>();
            defaultDataSourceProperties = binder.bind("spring.datasource", Map.class).get();
            // 获取数据源类型
            String typeStr = evn.getProperty("spring.datasource.type");
            // 获取数据源类型
            Class<? extends DataSource> clazz = getDataSourceType(typeStr);
            // 绑定默认数据源参数 也就是主数据源
            DataSource consumerDatasource, defaultDatasource = bind(clazz, defaultDataSourceProperties);
//            registerTxBean(annotationMetadata,beanDefinitionRegistry,defaultDatasource,"tx-master");
            DynamicDataSourceContextHolder.dataSourceIds.add("master");
            log.info("注册默认数据源成功");
            // 获取其他数据源配置
            List<Map> configs = binder.bind("spring.datasource.cluster", Bindable.listOf(Map.class)).get();
            // 遍历从数据源
            for (int i = 0; i < configs.size(); i++) {
                config = configs.get(i);
                clazz = getDataSourceType((String) config.get("type"));
                defaultDataSourceProperties = config;
                // 绑定参数
                consumerDatasource = bind(clazz, defaultDataSourceProperties);
                // 获取数据源的key，以便通过该key可以定位到数据源
                String key = config.get("key").toString();
                String txKey = config.get("transaction-key").toString();
                customDataSources.put(key, consumerDatasource);
//                registerTxBean(annotationMetadata,beanDefinitionRegistry,consumerDatasource,txKey);
                // 数据源上下文，用于管理数据源与记录已经注册的数据源key
                DynamicDataSourceContextHolder.dataSourceIds.add(key);
                log.info("注册数据源{}成功", key);
            }
            // bean定义类
            GenericBeanDefinition define = new GenericBeanDefinition();
            // 设置bean的类型，此处 MultipleDataSource 是继承AbstractRoutingDataSource的实现类
            define.setBeanClass(MultipleDataSource.class);
            // 需要注入的参数
            MutablePropertyValues mpv = define.getPropertyValues();
            // 添加默认数据源，避免key不存在的情况没有数据源可用
            mpv.add("defaultTargetDataSource", defaultDatasource);
            // 添加其他数据源
            mpv.add("targetDataSources", customDataSources);
            // 将该bean注册为datasource，不使用springboot自动生成的datasource
            beanDefinitionRegistry.registerBeanDefinition("dataSource", define);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void registerTxBean(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry,DataSource dataSource,String txName){
//        GenericBeanDefinition defineTx = new GenericBeanDefinition();
//        System.out.println("===========1111111111111111111===============");
//        System.out.println(dataSource);
//        System.out.println("===========1111111111111111111===============");
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
//        defineTx.setBeanClass(DataSourceTransactionManager.class);
//        defineTx.setSource(dataSourceTransactionManager);
//        beanDefinitionRegistry.registerBeanDefinition(txName, defineTx);
//    }


    /**
     * EnvironmentAware接口的实现方法，通过aware的方式注入，此处是environment对象
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        log.info("开始注册数据源");
        this.evn = environment;
        // 绑定配置器
        binder = Binder.get(evn);
    }

    /**
     * 通过字符串获取数据源class对象
     *
     * @param typeStr
     * @return
     */
    private Class<? extends DataSource> getDataSourceType(String typeStr) {
        Class<? extends DataSource> type;
        try {
            if (StringUtils.hasLength(typeStr)) {
                // 字符串不为空则通过反射获取class对象
                type = (Class<? extends DataSource>) Class.forName(typeStr);
            } else {
                // 默认为 DruidDataSource 数据源，与springboot默认数据源保持一致
                type = DruidDataSource.class;
            }
            return type;
        } catch (Exception e) {
            //无法通过反射获取class对象的情况则抛出异常，该情况一般是写错了，所以此次抛出一个runtimeexception
            throw new IllegalArgumentException("can not resolve class with type: " + typeStr);
        }
    }

    /**
     * 绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
     *
     * @param result
     * @param properties
     */
    private void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 将参数绑定到对象
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

    private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 通过类型绑定参数并获得实例对象
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    /**
     * @param clazz
     * @param sourcePath 参数路径，对应配置文件中的值，如: spring.datasource
     * @param <T>
     * @return
     */
    private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
        Map properties = binder.bind(sourcePath, Map.class).get();
        return bind(clazz, properties);
    }


}
