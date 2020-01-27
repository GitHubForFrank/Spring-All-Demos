package com.zmz.app.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author ASNPHDG
 * @create 2020-01-27 15:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Programmer {

    private String name;
    private int age;
    private float salary;
    private LocalDate birthday;

}