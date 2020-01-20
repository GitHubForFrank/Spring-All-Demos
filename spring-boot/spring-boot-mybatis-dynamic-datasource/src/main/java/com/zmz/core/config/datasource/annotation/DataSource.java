package com.zmz.core.config.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切换数据注解 可以用于类或者方法级别 方法级别优先级 > 类级别
 * @author ASNPHDG
 * @create 2020-01-13 11:36 AM
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /**
     * 该值即key值
     * @return
     */
    String value() default "master";
}