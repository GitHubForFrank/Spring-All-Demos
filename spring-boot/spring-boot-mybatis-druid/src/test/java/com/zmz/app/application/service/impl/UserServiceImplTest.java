package com.zmz.app.application.service.impl;

import com.zmz.app.SpringBootDruidMybatisApplication;
import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 22:05
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={SpringBootDruidMybatisApplication.class})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void test_query(){
        System.out.println("======================================");
        System.out.println("开始测试 queryAllUser(使用XML配置方式 )......");
        List<UserModel> list = userService.queryAllUser();

        if(list==null){
            System.out.println("未查询到数据。。。");
            return;
        }

        for (UserModel userModel : list) {
            System.out.println(userModel.toString());
        }

        System.out.println("======================================");
        System.out.println("开始测试 queryById(使用注解方式 )......");
        UserModel userModel = userService.queryById(list.get(0).getId());
        System.out.println(userModel.toString());
    }

//    @Test
    public void test_create(){
        UserModel userModel = new UserModel("xiaoming","IT","110","abc");
        userService.create(userModel);
    }

}