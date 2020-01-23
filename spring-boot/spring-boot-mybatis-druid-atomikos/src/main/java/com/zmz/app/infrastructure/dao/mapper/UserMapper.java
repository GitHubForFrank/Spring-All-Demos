package com.zmz.app.infrastructure.dao.mapper;

import com.zmz.app.infrastructure.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Mapper
public interface UserMapper {
    UserEntity selectByPrimaryKey(Long id);
    List<UserEntity> queryAllUser();
    void insert(UserEntity user);
    void deleteById(Long id);
    void updateByPrimaryKeySelective(UserEntity user);
}
