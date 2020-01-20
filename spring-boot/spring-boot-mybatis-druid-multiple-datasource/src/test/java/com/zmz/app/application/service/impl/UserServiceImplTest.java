package com.zmz.app.application.service.impl;

import com.zmz.app.Application;
import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ASNPHDG
 * @create 2020-01-13 4:57 PM
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={Application.class})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;


    @Test
    public void test_can_01(){
        //DB1 事务应该回滚
        UserModel userModel = new UserModel();
        userModel.setId(9);
        userModel.setName("test101");
        userService.updateUser(userModel);
    }


    @Test
    public void test_can_02(){
        //DB2 事务不应该回滚
        UserModel userModel = new UserModel();
        userModel.setName("99999");
        userModel.setDept("123");
        userModel.setPhone("1");
        userModel.setWebsite("123");
        userService.create(userModel);
    }

}