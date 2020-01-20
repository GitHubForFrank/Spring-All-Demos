package com.zmz.app.service;

import com.zmz.app.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-17 22:01
 */
public interface UserService {
    UserModel queryById(long id);
    List<UserModel> queryAllUser();
    void create(UserModel userModel);
    void delete(long id);
    void updateUser(UserModel userModel);
}
