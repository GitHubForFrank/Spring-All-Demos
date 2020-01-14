package com.zmz.core.config.datasource;

/**
 * @author ASNPHDG
 * @create 2020-01-13 3:24 PM
 */
public enum DataSourceEnum {

    DB1("db1"),DB2("db2");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}