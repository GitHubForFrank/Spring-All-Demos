package com.zmz.app.application.service.impl;

import com.zmz.app.application.service.UserService01;
import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-16 22:50
 */
@Service
public class UserService01Impl implements UserService01 {

    @Autowired
    private UserRepository01 userRepository;

    @Override
    public UserModel queryById(long id) {
        return userRepository.queryById(id);
    }

    @Override
    public List<UserModel> queryAllUser() {
        return userRepository.queryAllUser();
    }

    @Override
    @Transactional
    public void create(UserModel userModel) {
        userRepository.create(userModel);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(id);
    }

    @Override
    public void updateUser(UserModel userModel) {
        userRepository.updateUser(userModel);
    }

}

