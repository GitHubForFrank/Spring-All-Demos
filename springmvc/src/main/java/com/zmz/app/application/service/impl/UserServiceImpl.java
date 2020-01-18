package com.zmz.app.application.service.impl;

import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
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
    public UserModel queryById(long id) {
        return userRepository.queryById(id);
    }

    @Override
    public List<UserModel> queryAllUser() {
        return userRepository.queryAllUser();
    }

    @Override
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
