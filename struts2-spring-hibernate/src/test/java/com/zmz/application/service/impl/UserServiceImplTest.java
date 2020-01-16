package com.zmz.application.service.impl;

import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-05 23:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void can_run_DB(){
        System.out.println("999");
    }

    @Test
    public void can_run_findAllUser(){
        List<UserModel> list = userService.findAllUser();
        System.out.println(list.get(0).toString());
    }

    @Test
    public void can_run_insertUser(){
        List<UserModel> list = userService.findAllUser();
        UserModel userModel01 = list.get(0);
        userModel01.setId(null);
        userService.insertUser(userModel01);
    }

    @Test
    public void can_run_FindById(){
        UserModel user = userService.findUserByUserId(1l);
        System.out.println(user.toString());
    }

    @Test
    public void can_run_Delete(){
        userService.deleteUserByUserId(19L);
    }

    @Test
    public void can_run_findAll_ByCondition(){
        List<UserModel> users = userService.findAllByName("Name01");
        for(UserModel userModel : users){
            System.out.println(userModel.toString());
        }
    }

    @Test
    public void can_run_findAll_ByPage(){
        List<UserModel> users = userService.findAllByPage(2, 2, null);
        for(UserModel userModel : users){
            System.out.println(userModel.toString());
        }
    }

}