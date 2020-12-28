package com.zmz.infrastructure.translator;

import com.zmz.domain.model.UserModel;
import com.zmz.infrastructure.dao.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-12 7:52
 */
@Component
public class UserTranslator implements Translator<UserEntity, UserModel> {

    @Override
    public UserModel E2VO(UserEntity entity, UserModel vo) {
        if(entity == null){
            return vo;
        }

        if(vo == null){
            vo = new UserModel();
        }
        BeanUtils.copyProperties(entity,vo);
        return vo;
    }

    @Override
    public UserEntity VO2E(UserEntity entity, UserModel vo) {
        if(null == vo){
            return entity;
        }
        if(entity == null) {
            entity = new UserEntity();
        }
        BeanUtils.copyProperties(vo,entity);
        return entity;
    }

}
