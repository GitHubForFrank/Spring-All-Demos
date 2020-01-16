package com.zmz.app.infrastructure.repository.sqlserver;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository01;
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
public class UserRepository01Impl implements UserRepository01 {

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
    @DataSource(DataSourceEnum.SLAVE)
    public void create(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        try{
            userMapper.insert(userEntity);
            //人为产生异常
            int i = 4/0;
        }catch(Exception e){
            e.printStackTrace();
            //抛出Exception异常
            throw new RuntimeException  ("人为产生异常");
        }
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
