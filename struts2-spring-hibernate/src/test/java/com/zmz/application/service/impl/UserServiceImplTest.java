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
 * @create 2020-01-05 23:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void can_run_DB(){
        userService.findAllUser();
        System.out.println("999");
    }

    @Test
    public void can_run_findAllUser(){
        List<UserModel> list = userService.findAllUser();
        System.out.println(list.get(0).toString());
    }

    @Test
    public void can_run_insertUser(){
        //查出DB在插入，居然失败，有待研究
//        List<UserModel> list = userService.findAllUser();
//        UserModel userModel01 = list.get(0);
//        userModel01.setId(null);
//        userService.insertUser(userModel01);

        //新对象插入是成功的
        UserModel userModel = new UserModel();
        userModel.setName("456");
        userService.insertUser(userModel);
    }

//    @Test
//    public void can_run_FindById(){
//        UserModel user = userService.findUserByUserId(1l);
//        System.out.println(user.getRecordChangedTime());
//    }
//
//    @Test
//    public void can_run_SaveOrUpdate(){
//        UserModel domain = new UserModel();
//        //domain.setUserId(12l);// 注释掉执行测试create ; 不注释的话就测试update
//        domain.setRecordChangedTime(new Date());
//        domain.setUserName("2356");
//        userService.insertUser(domain);
//    }
//
//    @Test
//    public void can_run_Delete(){
//        userService.deleteUserByUserId(123124l);
//    }
//
//    @Test
//    public void can_run_FindAll(){
//        List<UserModel> users = userService.findAllUser();
//        for(UserModel u : users){
//            System.out.println(u.getUserId());
//        }
//    }
//
//    @Test
//    public void can_run_findAll_ByCondition(){
//        List<UserModel> users = userService.findAllActiveUser();
//        for(UserModel u : users){
//            System.out.println(u.getUserId());
//        }
//    }
//
//    @Test
//    public void can_run_findAll_ByPage(){
//        List<UserModel> userss = userService.findAllActiveUser();
//        List<UserModel> users = userService.findAllByPage(2, 2, null);
//        for(UserModel u : users){
//            System.out.println(u.getUserId());
//        }
//    }
//
}