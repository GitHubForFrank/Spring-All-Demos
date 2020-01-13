package com.zmz.application.service;

import com.zmz.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:49
 */
public interface UserService {

    void insertUser(UserModel userModel);
    void updateUser(UserModel userModel);
    UserModel findUserByUserId(long userId);
    void deleteUserByUserId(long userId);

    List<UserModel> findAllByName();
    List<UserModel> findAllUser();
    List<UserModel> findAllByPage(Integer pageNum, Integer pageCount,Object... params);

}
