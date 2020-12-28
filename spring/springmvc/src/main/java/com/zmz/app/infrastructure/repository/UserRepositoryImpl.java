package com.zmz.app.infrastructure.repository;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
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
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("name01");
        userModel.setDept("dept01");
        userModel.setPhone("phone01");
        userModel.setWebsite("website01");
        return userModel;
    }

    @Override
    public List<UserModel> queryAllUser() {
        List<UserModel> list = new ArrayList<>();
        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setName("name01");
        userModel.setDept("dept01");
        userModel.setPhone("phone01");
        userModel.setWebsite("website01");
        list.add(userModel);
        return list;
    }

    @Override
    public void create(UserModel userModel) {
        System.out.println("创建成功");
        if(userModel!=null){
            System.out.println(userModel.toString());
        }
    }

    @Override
    public void delete(long id) {
        System.out.println("删除成功");
    }

    @Override
    public void updateUser(UserModel userModel) {
        System.out.println("Update成功");
    }
}
