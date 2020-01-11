package com.zmz.mybatis.domain.repository;

import com.zmz.mybatis.infrastructure.dao.entity.User;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public interface UserRepository {

    User selectByPrimaryKey(Long id);
    List<User> queryAllUser();


}
