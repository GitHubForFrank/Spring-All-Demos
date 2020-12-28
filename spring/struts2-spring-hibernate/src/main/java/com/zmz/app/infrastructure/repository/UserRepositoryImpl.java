package com.zmz.app.infrastructure.repository;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import com.zmz.app.infrastructure.dao.entity.UserEntity;
import com.zmz.app.infrastructure.translator.UserTranslator;
import com.zmz.core.infrastructure.dao.Dao;
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
    public UserModel findUserByUserId(Long userId) {
        UserEntity userEntity = (UserEntity)dao.get(UserEntity.class, userId);
        return userTranslator.E2VO(userEntity,null);
    }

    @Override
    public void deleteUserByUserId(Long userId) {
        String hql = "delete from UserEntity as u where u.id = ?0 ";
        dao.delete(hql,userId);
    }

    @Override
    public List<UserModel> findAllByName(String name) {
        String hql = "from UserEntity where name = ?0 ";
        List<UserEntity> entityList = dao.getList(hql,name);
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
