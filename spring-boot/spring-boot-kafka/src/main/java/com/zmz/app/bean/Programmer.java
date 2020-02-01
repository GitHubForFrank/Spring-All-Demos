package com.zmz.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 需要实现序列化接口
 * @author ASNPHDG
 * @create 2020-02-01 13:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Programmer implements Serializable {
    private String name;
    private int age;
    private float salary;
    private Date birthday;
}

