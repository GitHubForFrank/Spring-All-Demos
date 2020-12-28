package com.zmz.app.domain.repository;

import com.zmz.app.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:55
 */
public interface UserRepository {

    void insertUser(UserModel userModel);
    void updateUser(UserModel userModel);
    UserModel findUserByUserId(Long userId);
    void deleteUserByUserId(Long userId);

    List<UserModel> findAllByName(String name);
    List<UserModel> findAllUser();
    List<UserModel> findAllByPage(Integer pageNum, Integer pageCount,Object... params);
}
