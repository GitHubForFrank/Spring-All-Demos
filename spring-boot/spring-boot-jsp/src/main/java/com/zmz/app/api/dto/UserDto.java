package com.zmz.app.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author ASNPHDG
 * @create 2020-01-26 20:27
 */
@Getter
@Setter
public class UserDto {

    private String name;
    private int age;
    private float salary;
    private LocalDate birthday;

    public UserDto(String name, int age, float salary, LocalDate birthday) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", birthday=" + birthday +
                '}';
    }
}
