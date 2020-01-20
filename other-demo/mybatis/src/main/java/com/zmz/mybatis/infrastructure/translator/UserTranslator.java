package com.zmz.mybatis.infrastructure.translator;

import com.zmz.mybatis.domain.model.UserModel;
import com.zmz.mybatis.infrastructure.dao.entity.UserEntity;

/**
 * @author ASNPHDG
 * @create 2020-01-12 7:52
 */
public class UserTranslator implements Translator<UserEntity, UserModel> {

    @Override
    public UserModel E2VO(UserEntity entity, UserModel vo) {
        if(entity == null){
            return vo;
        }

        if(vo == null){
            vo = new UserModel();
        }
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setDept(entity.getDept());
        vo.setPhone(entity.getPhone());
        vo.setWebsite(entity.getWebsite());
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
        entity.setId(vo.getId());
        entity.setName(vo.getName());
        entity.setDept(vo.getDept());
        entity.setPhone(vo.getPhone());
        entity.setWebsite(vo.getWebsite());
        return entity;
    }

}
