package com.zmz.core.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author ASNPHDG
 * @create 2020-01-13 11:41 AM
 */
@Slf4j
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceRouterKey();
        log.info(" 当前数据源是 : {} ", dataSourceName);
        return DynamicDataSourceContextHolder.getDataSourceRouterKey();
    }

}
