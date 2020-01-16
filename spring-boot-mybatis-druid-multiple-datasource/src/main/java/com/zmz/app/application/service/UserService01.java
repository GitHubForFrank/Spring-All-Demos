package com.zmz.app.application.service;

import com.zmz.app.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-16 22:49
 */
public interface UserService01 {
    UserModel queryById(long id);
    List<UserModel> queryAllUser();
    void create(UserModel userModel);
    void delete(long id);
    void updateUser(UserModel userModel);
}

