package com.zmz.app.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:52
 */
@Setter
@Getter
public class UserModel {

    private long id;
    private String name;
    private String dept;
    private String phone;
    private String website;

    public UserModel() {
    }

    public UserModel(String name, String dept, String phone, String website) {
        this.name = name;
        this.dept = dept;
        this.phone = phone;
        this.website = website;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
