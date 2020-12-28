package com.zmz.app.domain.repository;


import com.zmz.app.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public interface UserRepository01 {

    UserModel queryById(long id);
    List<UserModel> queryAllUser();
    void create(UserModel userModel);
    void delete(long id);
    void updateUser(UserModel userModel);
}
