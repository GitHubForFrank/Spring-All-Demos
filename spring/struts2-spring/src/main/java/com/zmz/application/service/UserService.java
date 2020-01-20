package com.zmz.application.service;

import com.zmz.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:49
 */
public interface UserService {
    UserModel queryById(long id);
    List<UserModel> queryAllUser();
    void create(UserModel userModel);
    void delete(long id);
    void updateUser(UserModel userModel);
}
