package com.zmz.mybatis.infrastructure.repository;

import com.zmz.mybatis.constants.MybatisConstant;
import com.zmz.mybatis.domain.repository.UserRepository;
import com.zmz.mybatis.helper.RepositoryHelperImpl;
import com.zmz.mybatis.infrastructure.dao.entity.User;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public class UserRepositoryImpl implements UserRepository {

    private RepositoryHelperImpl repositoryHelper = new RepositoryHelperImpl(MybatisConstant.MYBATIS_CONFIG_FILE_PATH);

    @Override
    public User selectByPrimaryKey(Long id) {
        return repositoryHelper.selectOne(User.class,"selectByPrimaryKey",id);
    }

    @Override
    public List<User> queryAllUser() {
        return repositoryHelper.selectList(User.class,"queryAllUser",null);
    }


}
