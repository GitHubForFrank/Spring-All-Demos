package com.zmz.app.service.impl;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-17 22:01
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserModel queryById(long id) {
        return null;
    }

    @Override
    public List<UserModel> queryAllUser() {
        return null;
    }

    @Override
    public void create(UserModel userModel) {
        System.out.println("方法执行中.....");
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void updateUser(UserModel userModel) {

    }
}
