package com.zmz.app.infrastructure.dao.mapper;

import com.zmz.app.infrastructure.dao.entity.UserEntity;
import com.zmz.core.config.datasource.DataSourceEnum;
import com.zmz.core.config.datasource.annotation.DataSource;
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

    @DataSource(DataSourceEnum.DB2)
    void insert(UserEntity user);

    @DataSource
    void deleteById(Long id);

    @DataSource
    void updateByPrimaryKeySelective(UserEntity user);
}
