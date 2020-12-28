package com.zmz.app.dao;

import com.zmz.app.bean.Programmer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ASNPHDG
 * @create 2020-01-29 17:57
 */
@Mapper
public interface ProgrammerDao {

    @Insert("insert into programmer (name, age, salary, birthday) VALUES (#{name}, #{age}, #{salary}, #{birthday})")
    void save(Programmer programmer);

    @Select("select * from programmer where id = #{id}")
    Programmer selectById(int id);

    @Update("update programmer set name=#{name},age=#{age},salary=#{salary},birthday=#{birthday} where id=#{id}")
    int modify(Programmer programmer);

    @Delete(" delete from programmer where id = #{id}")
    void delete(int id);
}
