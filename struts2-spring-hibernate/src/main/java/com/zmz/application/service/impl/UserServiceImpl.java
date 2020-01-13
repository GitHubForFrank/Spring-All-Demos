package com.zmz.application.service.impl;

import com.zmz.application.service.UserService;
import com.zmz.domain.model.UserModel;
import com.zmz.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void insertUser(UserModel userModel) {
        userRepository.insertUser(userModel);
    }

    @Override
    public void updateUser(UserModel userModel) {
        userRepository.updateUser(userModel);
    }

    @Override
    public UserModel findUserByUserId(long userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public void deleteUserByUserId(long userId) {
        userRepository.deleteUserByUserId(userId);
    }

    @Override
    public List<UserModel> findAllByName() {
        return userRepository.findAllByName();
    }

    @Override
    public List<UserModel> findAllUser() {
        return userRepository.findAllUser();
    }

    @Override
    public List<UserModel> findAllByPage(Integer pageNum, Integer pageCount, Object... params) {
        return userRepository.findAllByPage(pageNum,pageCount,params);
    }

}
