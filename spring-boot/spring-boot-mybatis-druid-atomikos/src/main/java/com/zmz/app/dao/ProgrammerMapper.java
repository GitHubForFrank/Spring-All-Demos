package com.zmz.app.dao;

import com.zmz.app.bean.Programmer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-30 18:47
 */
@Mapper
public interface ProgrammerMapper {

    List<Programmer> selectAll(String dataSource);

    void save(Programmer programmer);

    Programmer selectById(int id);

    int modify(String dataSource,@Param("pro") Programmer programmer);

    void delete(int id);
}
