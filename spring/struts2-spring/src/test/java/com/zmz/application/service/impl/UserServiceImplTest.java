package com.zmz.application.service.impl;

import com.zmz.application.service.UserService;
import com.zmz.domain.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @author ASNPHDG
 * @create 2020-01-05 9:55
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService UserService;

    @Test
    public void test_01(){
        List<UserModel> list = UserService.queryAllUser();
        System.out.println(list.size());
    }
}