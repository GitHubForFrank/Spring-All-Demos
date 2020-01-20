package com.zmz.app.application.service.impl;

import com.zmz.app.Application;
import com.zmz.app.application.service.UserService;
import com.zmz.app.application.service.UserService01;
import com.zmz.app.domain.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author ASNPHDG
 * @create 2020-01-16 22:53
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={Application.class})
public class MultipleDataSourceIT {

    @Resource
    private UserService userService;

    @Resource
    private UserService01 userService01;

    @Test
    public void test_UserService(){
        UserModel userModel = new UserModel();
        userModel.setName("S00000");
        userModel.setDept("userService");
        userService.create(userModel);
    }

    @Test
    public void test_UserService01(){
        UserModel userModel = new UserModel();
        userModel.setName("S11111");
        userModel.setDept("userService01");
        userService01.create(userModel);
    }

}
