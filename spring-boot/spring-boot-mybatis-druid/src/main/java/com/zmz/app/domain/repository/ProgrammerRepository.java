package com.zmz.app.domain.repository;

import com.zmz.app.domain.model.ProgrammerModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 23:21
 */
public interface ProgrammerRepository {

    List<ProgrammerModel> selectAll();
    void save(ProgrammerModel programmerModel);
    ProgrammerModel selectById(int id);
    int modify(ProgrammerModel programmerModel);
    void delete(int id);

}
