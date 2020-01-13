package com.zmz.infrastructure.repository;

import com.zmz.domain.model.UserModel;
import com.zmz.domain.repository.UserRepository;
import com.zmz.infrastructure.dao.Dao;
import com.zmz.infrastructure.dao.entity.UserEntity;
import com.zmz.infrastructure.translator.UserTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private Dao dao;
    @Resource
    private UserTranslator userTranslator;

    @Override
    public void insertUser(UserModel userModel) {
        dao.save(userTranslator.VO2E(null,userModel));
    }

    @Override
    public void updateUser(UserModel userModel) {
        dao.update(userTranslator.VO2E(null,userModel));
    }

    @Override
    public UserModel findUserByUserId(long userId) {
        UserEntity userEntity = (UserEntity)dao.get(UserEntity.class, userId);
        return userTranslator.E2VO(userEntity,null);
    }

    @Override
    public void deleteUserByUserId(long userId) {
        String hql = "delete from UserEntity as u where u.id = ? ";
        dao.delete(hql,userId);
    }

    @Override
    public List<UserModel> findAllByName() {
        String hql = "from UserEntity where name = ? ";
        List<UserEntity> entityList = dao.getList(hql,"A");
        return userTranslator.E2VOs(entityList,null);
    }

    @Override
    public List<UserModel> findAllUser() {
        List<UserEntity> entityList = dao.getList("from UserEntity");
        return userTranslator.E2VOs(entityList,null);
    }

    @Override
    public List<UserModel> findAllByPage(Integer pageNum, Integer pageCount,Object... params) {
        String hql = "from UserEntity";
        List<UserEntity> entityList = dao.getAll(hql, pageNum, pageCount, params);
        return userTranslator.E2VOs(entityList,null);
    }

}
