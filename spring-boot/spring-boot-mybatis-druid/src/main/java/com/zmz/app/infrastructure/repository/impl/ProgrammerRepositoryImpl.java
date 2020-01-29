package com.zmz.app.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zmz.app.domain.model.ProgrammerModel;
import com.zmz.app.domain.repository.ProgrammerRepository;
import com.zmz.app.infrastructure.dao.entity.ProgrammerEntity;
import com.zmz.app.infrastructure.dao.mapper.ProgrammerMapper;
import com.zmz.app.infrastructure.repository.translator.ProgrammerTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 23:21
 */
@Repository
public class ProgrammerRepositoryImpl implements ProgrammerRepository {

    @Resource
    private ProgrammerMapper programmerMapper;
    @Autowired
    private ProgrammerTranslator programmerTranslator;

    @Override
    public List<ProgrammerModel> selectAll() {
        List<ProgrammerEntity> entityList = programmerMapper.selectList(
                new QueryWrapper<ProgrammerEntity>().lambda()
                        .orderByAsc(ProgrammerEntity::getId)
        );
        return programmerTranslator.E2VOs(entityList, null);
    }

    @Override
    public void save(ProgrammerModel programmerModel) {
        programmerMapper.insert(programmerTranslator.VO2E(null,programmerModel));
    }

    @Override
    public ProgrammerModel selectById(int id) {
        ProgrammerEntity entity = programmerMapper.selectOne(
                new QueryWrapper<ProgrammerEntity>().lambda()
                        .eq(ProgrammerEntity::getId, id)
        );
        return programmerTranslator.E2VO(entity,null);
    }

    @Override
    public int modify(ProgrammerModel programmerModel) {
        return programmerMapper.updateById(programmerTranslator.VO2E(null,programmerModel));
    }

    @Override
    public void delete(int id) {
        programmerMapper.deleteById(id);
    }

}
