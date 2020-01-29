package com.zmz.app.infrastructure.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zmz.app.infrastructure.dao.entity.ProgrammerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采用Mybatis Plus方式
 * @author ASNPHDG
 * @create 2020-01-28 20:55
 */
@Mapper
public interface ProgrammerMapper extends BaseMapper<ProgrammerEntity> {

//    @Select("select * from programmer")
//    List<ProgrammerEntity> selectAll();

//    @Insert("insert into programmer (name, age, salary, birthday) VALUES (#{name}, #{age}, #{salary}, #{birthday})")
//    void save(ProgrammerEntity programmer);
//
//    @Select("select * from programmer where name = #{id}")
//    ProgrammerEntity selectById(int id);
//
//    @Update("update programmer set name=#{name},age=#{age},salary=#{salary},birthday=#{birthday} where id=#{id}")
//    int modify(ProgrammerEntity programmer);
//
//    @Delete(" delete from programmer where id = #{id}")
//    void delete(int id);

}
