package com.zmz.core.config.datasource.aop;

import com.zmz.core.config.datasource.DataSourceContextHolder;
import com.zmz.core.config.datasource.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ASNPHDG
 * @create 2020-01-13 11:36 AM
 */
@Slf4j
@Aspect
@Component
@Order(-1900)
public class DataSourceAspect {

    protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<>();

    @Pointcut(value = "@within(com.zmz.core.config.datasource.annotation.DataSource) || @annotation(com.zmz.core.config.datasource.annotation.DataSource)")
    protected void datasourceAspect() {
    }

    @Before("datasourceAspect()")
    public void changeDataSource(JoinPoint point) {
        String key = determineDatasource(point);
        if (DataSourceContextHolder.dataSourceIds.contains(key)) {
            preDatasourceHolder.set(DataSourceContextHolder.getDataSourceRouterKey());
            DataSourceContextHolder.setDataSourceRouterKey(key);
        } else {
            log.info("数据源[{}]不存在，使用默认数据源 >{}", key, point.getSignature());
        }
    }

    @After("datasourceAspect()")
    public void restoreDataSourceAfterMethodExecution() {
        DataSourceContextHolder.setDataSourceRouterKey(preDatasourceHolder.get());
        preDatasourceHolder.remove();
    }

    private String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
        log.info("AOP 切换 Key 所在类 ：{}",targetClass.getName());
        log.info("AOP 切换 Key 为：{}",resultDS);
        return resultDS;
    }
    private String resolveDataSourceFromMethod(Class targetClass, String methodName) {
        Method m = findUniqueMethod(targetClass, methodName);
        if (m != null) {
            DataSource choDs = m.getAnnotation(DataSource.class);
            return resolveDataSourceName(choDs);
        }
        return null;
    }
    private String determinateDataSource(String classDS, String methodDS) {
        return methodDS == null ? classDS : methodDS;
    }
    private String resolveDataSourceFromClass(Class targetClass) {
        DataSource classAnnotation = (DataSource) targetClass.getAnnotation(DataSource.class);
        return null != classAnnotation ? resolveDataSourceName(classAnnotation) : null;
    }
    private String resolveDataSourceName(DataSource ds) {
        return ds == null ? null : ds.value();
    }
    private static Method findUniqueMethod(Class<?> clazz, String name) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

}