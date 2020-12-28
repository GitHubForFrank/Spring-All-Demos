package com.zmz.infrastructure.dao.mapper;


import com.zmz.infrastructure.dao.entity.UserEntity;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public interface UserMapper {

    UserEntity selectByPrimaryKey(Long id);
    List<UserEntity> queryAllUser();
    void insert(UserEntity user);
    void delete(Long id);
    void updateByPrimaryKeySelective(UserEntity user);
}
