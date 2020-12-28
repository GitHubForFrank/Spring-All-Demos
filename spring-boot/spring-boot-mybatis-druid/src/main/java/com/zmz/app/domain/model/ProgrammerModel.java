package com.zmz.app.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author ASNPHDG
 * @create 2020-01-28 21:47
 */
@Setter
@Getter
public class ProgrammerModel {
    private int id;
    private String name;
    private int age;
    private float salary;
    private Date birthday;

    public ProgrammerModel() {
    }

    public ProgrammerModel(String name, int age, float salary, Date birthday) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "ProgrammerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", birthday=" + birthday +
                '}';
    }

}
