package com.zmz.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserModel {

    private Long id;
    private String name;
    private String dept;
    private String phone;
    private String website;

    public UserModel(String name, String dept, String phone, String website) {
        this.name = name;
        this.dept = dept;
        this.phone = phone;
        this.website = website;
    }
}
