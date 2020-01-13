package com.zmz.domain.repository;

import com.zmz.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public interface UserRepository {

    void insertUser(UserModel userModel);
    void updateUser(UserModel userModel);
    UserModel findUserByUserId(long userId);
    void deleteUserByUserId(long userId);

    List<UserModel> findAllByName();
    List<UserModel> findAllUser();
    List<UserModel> findAllByPage(Integer pageNum, Integer pageCount,Object... params);
}
