package com.zmz.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ASNPHDG
 * @create 2020-01-27 12:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Programmer implements Serializable {
    private String name;
    private int age;
    private float salary;
    private Date birthday;
}