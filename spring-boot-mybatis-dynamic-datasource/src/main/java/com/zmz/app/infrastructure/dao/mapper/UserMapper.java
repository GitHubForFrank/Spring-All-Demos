package com.zmz.app.infrastructure.dao.mapper;

import com.zmz.app.infrastructure.dao.entity.UserEntity;
import com.zmz.core.config.datasource.annotation.DataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@DataSource
@Mapper
public interface UserMapper {
    UserEntity selectByPrimaryKey(Long id);
    @DataSource
    List<UserEntity> queryAllUser();
    void insert(UserEntity user);
    void delete(Long id);
    void updateByPrimaryKeySelective(UserEntity user);
}
