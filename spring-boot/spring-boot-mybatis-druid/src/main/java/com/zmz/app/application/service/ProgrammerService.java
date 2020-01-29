package com.zmz.app.application.service;

import com.zmz.app.domain.model.ProgrammerModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 21:48
 */
public interface ProgrammerService {

    List<ProgrammerModel> selectAll();
    void save(ProgrammerModel programmerModel);
    ProgrammerModel selectById(int id);
    int modify(ProgrammerModel programmerModel);
    void delete(int id);

}
