package com.zmz.app.infrastructure.dao.mapper;

import com.zmz.app.infrastructure.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Mapper
public interface UserMapper {

    @Select("select * from tbl_user where id = #{id,jdbcType=BIGINT}")
    UserEntity selectByPrimaryKey(@Param("id") Long id);

    List<UserEntity> queryAllUser();
    void insert(UserEntity user);

    @Delete("delete from tbl_user where id = #{id,jdbcType=BIGINT}")
    void deleteById(@Param("id") Long id);

    void updateByPrimaryKeySelective(UserEntity user);

}
