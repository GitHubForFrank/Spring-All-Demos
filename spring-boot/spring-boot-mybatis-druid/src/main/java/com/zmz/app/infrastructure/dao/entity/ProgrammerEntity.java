package com.zmz.app.infrastructure.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("programmer")
public class ProgrammerEntity extends Model<ProgrammerEntity> {

    private int id;
    private String name;
    private int age;
    private float salary;
    private Date birthday;

    public ProgrammerEntity(String name, int age, float salary, Date birthday) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.birthday = birthday;
    }

}
