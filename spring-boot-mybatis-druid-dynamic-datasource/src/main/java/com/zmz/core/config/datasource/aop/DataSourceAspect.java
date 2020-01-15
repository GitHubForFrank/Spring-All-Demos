package com.zmz.core.config.datasource.aop;

import com.zmz.core.config.datasource.DynamicDataSourceContextHolder;
import com.zmz.core.config.datasource.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-13 3:25 PM
 */
@Slf4j
@Component
@Aspect
@Order(-1)
public class DataSourceAspect {

    @Before("@annotation(dataSource)")
    public void doBefore(DataSource dataSource){
        log.info("选择数据源---"+dataSource.value());
        DynamicDataSourceContextHolder.setDataSourceRouterKey(dataSource.value());
    }

    @After("@annotation(dataSource)")
    public void doAfter(DataSource dataSource){
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }
}