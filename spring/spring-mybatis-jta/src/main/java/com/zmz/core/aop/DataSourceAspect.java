package com.zmz.core.aop;

import com.zmz.core.annotation.DataSource;
import com.zmz.core.datasource.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;


/**
 * <li>类描述：完成数据源的切换，抽类切面，具体项目继承一下，不需要重写即可使用</li>
 * @author ASNPHDG
 * @create 2020-01-25 14:18
 */
@Slf4j
@Component
@Aspect
@Order(-1)
public class DataSourceAspect {

    protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<>();

    @Pointcut(value = "@within(com.zmz.core.annotation.DataSource) || @annotation(com.zmz.core.annotation.DataSource)")
    public void datasourceAspect() {
    }

    @Before("datasourceAspect()")
    public void changeDataSourceBeforeMethodExecution(JoinPoint jp) {
        String key = determineDatasource(jp);
        if (key == null) {
            DataSourceContextHolder.setDataSource(null);
            return;
        }
        preDatasourceHolder.set(DataSourceContextHolder.getDataSource());
        DataSourceContextHolder.setDataSource(key);

    }

    public String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
        log.info("AOP 切换 Key 所在类 ：{}",targetClass.getName());
        log.info("AOP 切换 Key 为：{}",resultDS);
        return resultDS;
    }

    @After("datasourceAspect()")
    public void restoreDataSourceAfterMethodExecution() {
        DataSourceContextHolder.setDataSource(preDatasourceHolder.get());
        preDatasourceHolder.remove();
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


    /**
     * <li>方法描述 : 寻找方法名唯 一的方法</li>
     * @param clazz
     * @param name
     * @return
     */
    private static Method findUniqueMethod(Class<?> clazz, String name) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");
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
