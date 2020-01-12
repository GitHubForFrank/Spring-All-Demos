package com.zmz.infrastructure.repository;

import com.zmz.domain.model.UserModel;
import com.zmz.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public UserModel queryById(long id) {
        UserModel model = new UserModel();
        return model;
    }

    @Override
    public List<UserModel> queryAllUser() {
        List<UserModel> listModel = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("name01");
        userModel.setDept("dept01");
        userModel.setPhone("phone01");
        userModel.setWebsite("website01");
        listModel.add(userModel);
        return listModel;
    }

    @Override
    public void create(UserModel userModel) {
        //TODO not handle currently
    }

    @Override
    public void delete(long id) {
        //TODO not handle currently
    }

    @Override
    public void updateUser(UserModel userModel) {
        //TODO not handle currently
    }

}
