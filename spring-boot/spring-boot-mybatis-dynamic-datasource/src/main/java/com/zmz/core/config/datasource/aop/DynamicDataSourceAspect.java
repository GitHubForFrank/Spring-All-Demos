package com.zmz.core.config.datasource.aop;

import com.zmz.core.config.datasource.DynamicDataSourceContextHolder;
import com.zmz.core.config.datasource.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-13 11:36 AM
 */
@Slf4j
@Aspect
@Component
@Order(-1900)
public class DynamicDataSourceAspect {

    @Before("@annotation(ds)")
    public void changeDataSource(JoinPoint point, DataSource ds) {
        String dsId = ds.value();
        log.info("================================================================================");
        if (DynamicDataSourceContextHolder.dataSourceIds.contains(dsId)) {
            log.info("Use DataSource :{} >", dsId, point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceRouterKey(dsId);
        } else {
            log.info("数据源[{}]不存在，使用默认数据源 >{}", dsId, point.getSignature());
        }
    }

    @After("@annotation(ds)")
    public void restoreDataSource(JoinPoint point, DataSource ds) {
        log.info("Revert DataSource : " + ds.value() + " > " + point.getSignature());
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
        log.info("================================================================================");
    }

}