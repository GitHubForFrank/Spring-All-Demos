package com.zmz.app.dao;

import com.zmz.app.bean.Programmer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ASNPHDG
 * @create 2020-01-29 17:57
 */
@Mapper
public interface ProgrammerMapper {

    void save(Programmer programmer);

    Programmer selectById(int id);

    int modify(Programmer programmer);

    void delete(int id);
}
