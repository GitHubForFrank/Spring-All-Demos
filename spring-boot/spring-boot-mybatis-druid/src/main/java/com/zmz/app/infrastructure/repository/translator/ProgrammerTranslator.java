package com.zmz.app.infrastructure.repository.translator;

import com.zmz.app.domain.model.ProgrammerModel;
import com.zmz.app.infrastructure.dao.entity.ProgrammerEntity;
import com.zmz.core.infrastructure.repository.translator.Translator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-28 23:22
 */
@Component
public class ProgrammerTranslator implements Translator<ProgrammerEntity, ProgrammerModel> {
    @Override
    public ProgrammerModel E2VO(ProgrammerEntity entity, ProgrammerModel vo) {
        if(entity == null){
            return vo;
        }

        if(vo == null){
            vo = new ProgrammerModel();
        }
        BeanUtils.copyProperties(entity,vo);
        return vo;
    }

    @Override
    public ProgrammerEntity VO2E(ProgrammerEntity entity, ProgrammerModel vo) {
        if(null == vo){
            return entity;
        }
        if(entity == null) {
            entity = new ProgrammerEntity();
        }
        BeanUtils.copyProperties(vo,entity);
        return entity;
    }
}
