package com.zmz.app.api.controller;

import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import com.zmz.app.domain.repository.UserRepository01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-30 22:44
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository01 userRepository01;

    @GetMapping("user/master/queryAll")
    public List<UserModel> queryAllUser() {
        return userRepository.queryAllUser();
    }

    @GetMapping("user/slave/queryAll")
    public List<UserModel> queryAllUser01() {
        return userRepository01.queryAllUser();
    }

}
