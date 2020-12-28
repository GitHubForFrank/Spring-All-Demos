package com.zmz.app.application.service.impl;

import com.zmz.app.application.service.ProgrammerService;
import com.zmz.app.domain.model.ProgrammerModel;
import com.zmz.app.domain.repository.ProgrammerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 21:48
 */
@Service
public class ProgrammerServiceImpl implements ProgrammerService {

    @Autowired
    private ProgrammerRepository programmerRepository;

    @Override
    public List<ProgrammerModel> selectAll() {
        return programmerRepository.selectAll();
    }

    @Override
    public void save(ProgrammerModel programmerModel) {
        programmerRepository.save(programmerModel);
    }

    @Override
    public ProgrammerModel selectById(int id) {
        return programmerRepository.selectById(id);
    }

    @Override
    public int modify(ProgrammerModel programmerModel) {
        return programmerRepository.modify(programmerModel);
    }

    @Override
    public void delete(int id) {
        programmerRepository.delete(id);
    }

}
