package com.zmz.core.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 /**
 * @author ASNPHDG
 * @create 2020-01-28 17:04
 * @description : 属性配置映射类
 */
@Component
@ConfigurationProperties(prefix = "programmer")
@Data
@ToString
public class Programmer {

    private String name;
    private int age;
    private boolean married;
    private Date hireDate;
    private float salary;
    private int random;
    private Map<String, String> skill;
    private List company;
    private School school;

}
