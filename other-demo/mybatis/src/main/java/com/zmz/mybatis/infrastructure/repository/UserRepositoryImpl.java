package com.zmz.mybatis.infrastructure.repository;

import com.zmz.mybatis.domain.model.UserModel;
import com.zmz.mybatis.domain.repository.UserRepository;
import com.zmz.mybatis.helper.RepositoryHelper;
import com.zmz.mybatis.helper.RepositoryHelperImpl;
import com.zmz.mybatis.infrastructure.dao.entity.UserEntity;
import com.zmz.mybatis.infrastructure.translator.UserTranslator;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public class UserRepositoryImpl implements UserRepository {

    RepositoryHelper repositoryHelper = RepositoryHelperImpl.getInstance();
    private UserTranslator userTranslator = new UserTranslator();

    @Override
    public UserModel queryById(long id) {
        UserEntity userEntity = repositoryHelper.selectOne(UserEntity.class,"selectByPrimaryKey",id);
        return userTranslator.E2VO(userEntity,null);
    }

    @Override
    public void create(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        repositoryHelper.insertObject(UserEntity.class,"insert",userEntity);
    }

    @Override
    public void delete(long id) {
        repositoryHelper.deleteObject(UserEntity.class,"deleteById",id);
    }

    @Override
    public void updateUser(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        repositoryHelper.updateObject(UserEntity.class,"updateByPrimaryKeySelective",userEntity);
    }

    @Override
    public List<UserModel> queryAllUser() {
        List<UserEntity> entityList = repositoryHelper.selectList(UserEntity.class,"queryAllUser",null);
        return userTranslator.E2VOs(entityList,null);
    }


}
