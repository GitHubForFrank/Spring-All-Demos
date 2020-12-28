package com.zmz.app.infrastructure.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {

    private Long id;
    private String name;
    private String dept;
    private String phone;
    private String website;

}