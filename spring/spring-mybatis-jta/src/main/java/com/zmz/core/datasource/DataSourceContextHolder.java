package com.zmz.core.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ASNPHDG
 * @create 2020-01-25 14:18
 * 将数据源的键存入ThreadLocal中
 */
@Slf4j
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dataSourceKey) {
        contextHolder.set(dataSourceKey);
    }

    public static String getDataSource() {
        String key = contextHolder.get();
        log.info("Thread:"+Thread.currentThread().getName()+"dataSource key is "+ key);
        return key;
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
