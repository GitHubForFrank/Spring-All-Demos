package com.zmz.app.infrastructure.dao.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Getter
@Setter
public class UserEntity {

    private Long id;
    private String name;
    private String dept;
    private String phone;
    private String website;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

}