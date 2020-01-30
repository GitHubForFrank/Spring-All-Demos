package com.zmz.app.infrastructure.repository.impl;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import com.zmz.app.infrastructure.dao.entity.UserEntity;
import com.zmz.app.infrastructure.dao.mapper.UserMapper;
import com.zmz.app.infrastructure.repository.translator.UserTranslator;
import com.zmz.core.config.datasource.annotation.DataSource;
import com.zmz.core.config.datasource.annotation.DataSourceEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Repository
@DataSource(DataSourceEnum.MASTER)
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserTranslator userTranslator;

    @Override
    public UserModel queryById(long id) {
        UserEntity userEntity = userMapper.selectByPrimaryKey(id);
        return userTranslator.E2VO(userEntity,null);
    }

    @Override
    public List<UserModel> queryAllUser() {
        List<UserEntity> entityList = userMapper.queryAllUser();
        return userTranslator.E2VOs(entityList,null);
    }

    @Override
    public void create(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        userMapper.insert(userEntity);
    }

    @Override
    public void delete(long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateUser(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        userMapper.updateByPrimaryKeySelective(userEntity);
    }

}
